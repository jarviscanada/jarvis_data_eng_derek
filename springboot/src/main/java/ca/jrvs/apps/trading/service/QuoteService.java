package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class QuoteService {
    private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);

    private QuoteDao quoteDao;
    private MarketDataDao marketDataDao;

    @Autowired
    public QuoteService(QuoteDao quoteDao, MarketDataDao marketDataDao) {
        this.quoteDao = quoteDao;
        this.marketDataDao = marketDataDao;
    }

    protected static Quote buildQuoteFromIexQuote(IexQuote iexQuote) {
        Quote quote = new Quote();
        quote.setTicker(iexQuote.getSymbol());
        quote.setLastPrice(iexQuote.getLatestPrice());
        quote.setBidPrice(iexQuote.getIexBidPrice());
        quote.setBidSize(iexQuote.getIexBidSize());
        quote.setAskPrice(iexQuote.getIexAskPrice());
        quote.setAskSize(iexQuote.getIexAskSize());
        return quote;
    }

    /**
     * Find an IexQuote
     *
     * @param ticker id
     * @return IexQuote object
     * @throws IllegalArgumentException if ticker is invalid
     */
    public IexQuote findIndexQuoteByTicker(String ticker) {
        return marketDataDao.findById(ticker).orElseThrow(() -> new IllegalArgumentException(ticker + " is invalid"));
    }

    public void updateMarketData() {
        List<Quote> allQuotes = quoteDao.findAll();
        List<Quote> updateQuotes = null;
        IexQuote iexQuote;
        for (Quote q : allQuotes) {
            iexQuote = findIndexQuoteByTicker(q.getId());
            updateQuotes.add(buildQuoteFromIexQuote(iexQuote));
        }
        quoteDao.saveAll(updateQuotes);
    }

    public List<Quote> saveQuotes(List<String> tickers) {
        List<Quote> listQuotes = new ArrayList<>();
        Quote quote = new Quote();
        List<IexQuote> listIexQuotes = marketDataDao.findAllById(tickers);
        for (IexQuote i : listIexQuotes) {
            quote = buildQuoteFromIexQuote(i);
            listQuotes.add(quote);
        }
        quoteDao.saveAll(listQuotes);
        return listQuotes;
    }

    public Quote saveQuote(String ticker) {
        IexQuote iexQuote = marketDataDao.findById(ticker).get();
        Quote quote = buildQuoteFromIexQuote(iexQuote);
        Quote saveQuote = quoteDao.save(quote);
        return saveQuote;
    }


    public Quote saveQuote(Quote quote) {
        return quoteDao.save(quote);
    }

    public List<Quote> findAllQuotes() {
        return quoteDao.findAll();
    }
}
