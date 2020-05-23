package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

import static ca.jrvs.apps.trading.util.JsonUtil.toObjectFromJson;

@Repository
public class MarketDataDao implements CrudRepository<IexQuote, String> {
    private static final String IEX_BATCH_PATH = "/stock/market/batch?symbols=%s&types=quote&token=";
    private static String IEX_BATCH_URL;

    private Logger logger = LoggerFactory.getLogger(MarketDataDao.class);
    private HttpClientConnectionManager httpClientConnectionManager;
    //private PoolingHttpClientConnectionManager httpClientConnectionManager;

    private String[] tickerFields = {"GOOGL", "FB", "AAPL", "MMM", "AMD", "APA"};

    @Autowired
    public MarketDataDao(HttpClientConnectionManager httpClientConnectionManager, MarketDataConfig marketDataConfig) {
        this.httpClientConnectionManager = httpClientConnectionManager;
        IEX_BATCH_URL = marketDataConfig.getHost() + IEX_BATCH_PATH + marketDataConfig.getToken();
    }

    public static void main(String[] args) {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(50);
        cm.setDefaultMaxPerRoute(50);
        MarketDataConfig marketDataConfig = new MarketDataConfig();
        marketDataConfig.setHost("https://cloud.iexapis.com/v1");
        marketDataConfig.setToken(System.getenv("IEX_PUB_TOKEN"));
        MarketDataDao dao = new MarketDataDao(cm, marketDataConfig);
        String ticker = "FB";
        IexQuote iexQuote = dao.findById(ticker).get();
        //System.out.println(iexQuote.getSymbol());
        List<IexQuote> quoteList = dao.findAllById(Arrays.asList("AAPL", "FB"));
        for (int i = 0; i < quoteList.size(); i++) {
            System.out.println(quoteList.get(i).getSymbol());
        }
    }

    /**
     * Borrow a HTTP client from the httpClientConnectionManger
     *
     * @return a httpClient
     */
    private CloseableHttpClient getHttpClient() {
        return HttpClients.custom().setConnectionManager(httpClientConnectionManager)
                .setConnectionManagerShared(true).build();
        //prevent connectionManager shutdown when calling httpClient.close()
    }

    /**
     * Execute a get and return http entity/body as a string
     * <p>
     * Use EntityUtils.toString to process HTTP entity
     *
     * @param url resource URL
     * @return http response body or Optional.empty for 404 response
     * @throws DataRetrievalFailureException if HTTP failed or status code if unexpected
     */
    private Optional<String> executeHttpGet(String url) throws IOException, URISyntaxException {
        HttpClient httpClient = getHttpClient();
        HttpGet request = new HttpGet(url);
        HttpResponse response;
        try {
            response = httpClient.execute(request);
        } catch (IOException e) {
            throw new MalformedURLException("Invalid URL" + e.getMessage());
        }
        int status = response.getStatusLine().getStatusCode();

        Optional<String> jsonStr;

        if (status != 200) {
            try {
                System.out.println(EntityUtils.toString(response.getEntity()));
            } catch (IOException e) {
                throw new RuntimeException("Failed to convert entity to String", e);
            }
            throw new DataRetrievalFailureException("Unexpected HTTP status " + status);
        }
        if (response.getEntity() == null) {
            throw new RuntimeException("Empty response body");
        }

        try {
            jsonStr = Optional.of(EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert entity to String", e);
        }
        return jsonStr;
    }

    @Override
    /**
     * This method calls the IEX endpoint and deserializes IEX JSON HTTP response into IexQuote. If the ticker (String s)
     * is not found, this method returns Optional.empty
     */
    public Optional<IexQuote> findById(String ticker) {
        Optional<IexQuote> iexQuote;
        List<IexQuote> quotes = findAllById(Collections.singletonList(ticker));

        if (quotes.size() == 0) {
            return Optional.empty();
        } else if (quotes.size() == 1) {
            iexQuote = Optional.of(quotes.get(0));
        } else {
            throw new DataRetrievalFailureException("Unexpected number of quotes");
        }
        return iexQuote;
    }

    @Override
    //Similar to findById except you can use IEX batch request to get multiple quotes with only one HTTP call.
    /**
     * Get quotes from IEX
     * @param tickers is a list of tickers
     * @return a list of IexQuote object
     * @throws IllegalArgumentException if any ticker is invalid or tickers is empty
     * @throws DataRetrievalFailureException if HTTP request failed
     */
    public List<IexQuote> findAllById(Iterable<String> tickers) {

        List<IexQuote> list = new ArrayList<>();
        List<String> tickerStr = new ArrayList<>();
        IexQuote iexQuote = null;
        Optional<String> str = null;
        String singeJsonStr;

        for (String s : tickers) {
            validateTicker(s);
            tickerStr.add(s);
        }
        if (tickerStr.size() == 0) {
            throw new IllegalArgumentException("Ticker is empty!");
        }
        String result = tickerStr.stream().collect(Collectors.joining(","));
        //put tickers in the URL
        String url = String.format(IEX_BATCH_URL, result);

        try {
            str = executeHttpGet(url);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        //construct JSONObject from JSON String
        JSONObject jsonObject = new JSONObject(str.get());
        for (String s : tickers) {
            singeJsonStr = jsonObject.getJSONObject(s).getJSONObject("quote").toString();
            try {
                iexQuote = toObjectFromJson(singeJsonStr, IexQuote.class);

            } catch (IOException e) {
                e.printStackTrace();
            }
            list.add(iexQuote);
        }
        return list;
    }

    private void validateTicker(String ticker) {
        if (!Arrays.asList(tickerFields).contains(ticker)) {
            throw new IllegalArgumentException(ticker + " is invalid!");
        }
    }

    @Override
    public boolean existsById(String s) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Iterable<IexQuote> findAll() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteById(String s) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void delete(IexQuote iexQuote) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends IexQuote> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public <S extends IexQuote> S save(S s) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public <S extends IexQuote> Iterable<S> saveAll(Iterable<S> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
