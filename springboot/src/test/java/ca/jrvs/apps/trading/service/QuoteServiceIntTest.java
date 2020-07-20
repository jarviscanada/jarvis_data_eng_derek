package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteServiceIntTest {

    @Autowired
    QuoteService quoteService;

    @Autowired
    QuoteDao quoteDao;

    private Quote saveQuoteOne = new Quote();
    private Quote saveQuoteTwo = new Quote();

    @Before
    public void insert() {

        saveQuoteOne.setAskPrice(20d);
        saveQuoteOne.setAskSize(10);
        saveQuoteOne.setBidPrice(10.2d);
        saveQuoteOne.setBidSize(10);
        saveQuoteOne.setTicker("AAPL");
        saveQuoteOne.setLastPrice(10.1d);
        quoteDao.save(saveQuoteOne);
    }

    @Test
    public void findIexQuoteByTicker() {
        IexQuote iexQuote = quoteService.findIndexQuoteByTicker("GOOGL");
        //System.out.println(iexQuote.getSymbol());
        assertNotNull(iexQuote);
    }

    @Test
    public void updateMarketData() {
        quoteService.updateMarketData();
        assertNotEquals(0, quoteDao.count());
    }


    @Test
    public void saveQuote() {
        quoteService.saveQuote(saveQuoteOne);
    }

    @Test
    public void saveQuotes() {
        List<String> tickers = new ArrayList<>();
        tickers.add("AAPL");
        tickers.add("GOOGL");
        quoteService.saveQuotes(tickers);
        assertEquals(2, quoteDao.count());
    }

    @Test
    public void findAllQuotes() {
        List<Quote> quotes = quoteService.findAllQuotes();
        assertEquals(1, quoteDao.count());
    }
}