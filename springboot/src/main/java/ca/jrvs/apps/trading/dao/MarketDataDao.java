package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ca.jrvs.apps.trading.util.JsonUtil.toObjectFromJson;

@Repository
public class MarketDataDao implements CrudRepository<IexQuote, String> {
    private static final Logger logger = LoggerFactory.getLogger(MarketDataDao.class);
    private static final String IEX_BATCH_PATH = "/stock/market/batch?symbols=%s&types=quote&token=";
    private static String IEX_BATCH_URL;
    private HttpClientConnectionManager httpClientConnectionManager;

    @Autowired
    public MarketDataDao(HttpClientConnectionManager httpClientConnectionManager, MarketDataConfig marketDataConfig) {
        this.httpClientConnectionManager = httpClientConnectionManager;
        IEX_BATCH_URL = marketDataConfig.getHost() + IEX_BATCH_PATH + marketDataConfig.getToken();
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
        try (CloseableHttpClient httpClient = getHttpClient()) {
            HttpGet httpGet = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                switch (response.getStatusLine().getStatusCode()) {
                    case 200:
                        //EntityUtils toString will also close inputStream in Entity
                        String body = EntityUtils.toString(response.getEntity());
                        return Optional.ofNullable(body);
                    case 404:
                        return Optional.empty();
                    default:
                        throw new DataRetrievalFailureException(
                                "Unexpected status:" + response.getStatusLine().getStatusCode());
                }
            }
        } catch (IOException e) {
            throw new DataRetrievalFailureException("Unable Http execution error", e);
        }
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
            throw new DataRetrievalFailureException("Invalid URL" + e.getMessage());
        }
        //construct JSONObject from JSON String
        JSONObject jsonObject = new JSONObject(str.get());
        for (String s : tickers) {
            singeJsonStr = jsonObject.getJSONObject(s).getJSONObject("quote").toString();
            try {
                iexQuote = toObjectFromJson(singeJsonStr, IexQuote.class);

            } catch (IOException e) {
                logger.error("Cannot convert JSON to quote");
            }
            list.add(iexQuote);
        }
        return list;
    }

    private void validateTicker(String ticker) {
        if (!ticker.matches("[a-zA-Z]{2,5}")) {
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
