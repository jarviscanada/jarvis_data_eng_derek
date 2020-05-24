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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

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
        saveQuoteTwo.setAskPrice(30d);
        saveQuoteTwo.setAskSize(10);
        saveQuoteTwo.setBidPrice(10.2d);
        saveQuoteTwo.setBidSize(10);
        saveQuoteTwo.setTicker("FB");
        saveQuoteTwo.setLastPrice(10.1d);
        quoteDao.save(saveQuoteTwo);
    }

    @Test
    public void findIexQuoteByTicker(){
        IexQuote iexQuote = quoteService.findIndexQuoteByTicker("AAPL");
           }
//
//    @Test
//    public void updateMarketData(){
//        quoteService.updateMarketData();
//
//    }
//
    @Test
    public void saveQuotes(){
        List<String> tickers = new ArrayList<>();
        tickers.add("AAPL");
        tickers.add("")
    }
//
//    @Test
//    public void saveQuote(){}

//    @Test
//    public void findAllQuotes() {
//        assertArrayEquals(quoteDao.findAll().toArray(), quoteService.findAllQuotes().toArray());
//    }

//    @After
//    public void deleteAll() {
//        quoteDao.deleteAll();
//    }
}
