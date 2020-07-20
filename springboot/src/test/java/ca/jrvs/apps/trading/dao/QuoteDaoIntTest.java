package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteDaoIntTest {
    @Autowired
    private QuoteDao quoteDao;

    private Quote saveQuote = new Quote();
    private Quote saveQuoteOne = new Quote();
    private Quote saveQuoteTwo = new Quote();

    @Before
    public void insertOne() {
        saveQuote.setAskPrice(10d);
        saveQuote.setAskSize(10);
        saveQuote.setBidPrice(10.2d);
        saveQuote.setBidSize(10);
        saveQuote.setTicker("0M");
        saveQuote.setLastPrice(10.1d);
        quoteDao.save(saveQuote);
    }

    @Test
    public void findById() {
        Optional<Quote> quote = quoteDao.findById(saveQuote.getTicker());
        assertEquals(saveQuote.getTicker(), quote.get().getTicker());
    }

    @Test
    public void saveAll() {
        saveQuoteOne.setAskPrice(10d);
        saveQuoteOne.setAskSize(10);
        saveQuoteOne.setBidPrice(10.2d);
        saveQuoteOne.setBidSize(10);
        saveQuoteOne.setTicker("1M");
        saveQuoteOne.setLastPrice(10.1d);
        saveQuoteTwo.setAskPrice(10d);
        saveQuoteTwo.setAskSize(10);
        saveQuoteTwo.setBidPrice(10.2d);
        saveQuoteTwo.setBidSize(10);
        saveQuoteTwo.setTicker("2M");
        saveQuoteTwo.setLastPrice(10.1d);
        List list = new ArrayList();
        list.add(saveQuoteOne);
        list.add(saveQuoteTwo);
        List quotes = quoteDao.saveAll(list);
        Optional<Quote> quoteOne = quoteDao.findById("1M");
        assertEquals(saveQuoteOne.getTicker(), quoteOne.get().getTicker());
        Optional<Quote> quoteTwo = quoteDao.findById("2M");
        assertEquals(saveQuoteTwo.getTicker(), quoteTwo.get().getTicker());
    }

    @After
    public void deleteAll() {
        quoteDao.deleteAll();
    }

}
