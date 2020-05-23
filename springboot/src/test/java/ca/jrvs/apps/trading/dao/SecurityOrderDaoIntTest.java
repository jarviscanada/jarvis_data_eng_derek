package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class SecurityOrderDaoIntTest {
   //There must be one @Autowired on top of each DAO
    @Autowired
    TraderDao traderDao;
    @Autowired
    AccountDao accountDao;
    @Autowired
    QuoteDao quoteDao;
    @Autowired
    SecurityOrderDao securityOrderDao;

    private SecurityOrder securityOrder;
    private SecurityOrder securityOrderTwo;
    private Account savedAccount;
    private Account savedAccountTwo;
    private Trader savedTrader;
    private Trader savedTraderTwo;
    private Quote savedQuote;
    private Quote savedQuoteTwo;

    @Before
    public void insert() {

        savedTrader = new Trader();
        savedTrader.setCountry("USA");
        savedTrader.setDob(new Date(1999, 03, 11));
        savedTrader.setEmail("dereksong88@yahoo.com");
        savedTrader.setFirst_name("Derek");
        savedTrader.setLast_name("Song");
        savedTrader.setId(traderDao.save(savedTrader).getId());
        traderDao.save(savedTrader);

        savedTraderTwo = new Trader();
        savedTraderTwo.setCountry("Canada");
        savedTraderTwo.setDob(new Date(1989, 03, 11));
        savedTraderTwo.setEmail("johnsong88@yahoo.com");
        savedTraderTwo.setFirst_name("John");
        savedTraderTwo.setLast_name("Song");
        savedTraderTwo.setId(traderDao.save(savedTraderTwo).getId());
        traderDao.save(savedTraderTwo);

        savedAccount = new Account();
        savedAccount.setTrader_id(savedTrader.getId());
        savedAccount.setAmount(100.0);
        savedAccount.setId(accountDao.save(savedAccount).getId());

        savedAccountTwo = new Account();
        savedAccountTwo.setTrader_id(savedTraderTwo.getId());
        savedAccountTwo.setAmount(200.0);
        savedAccountTwo.setId(accountDao.save(savedAccountTwo).getId());

        savedQuote = new Quote();
        savedQuote.setAskPrice(10.0);
        savedQuote.setAskSize(10);
        savedQuote.setBidPrice(11.0);
        savedQuote.setBidSize(11);
        savedQuote.setTicker("GOOGL");
        savedQuote.setLastPrice(22.0);
        quoteDao.save(savedQuote);

        savedQuoteTwo = new Quote();
        savedQuoteTwo.setAskPrice(20.0);
        savedQuoteTwo.setAskSize(20);
        savedQuoteTwo.setBidPrice(21.0);
        savedQuoteTwo.setBidSize(21);
        savedQuoteTwo.setTicker("FB");
        savedQuoteTwo.setLastPrice(32.0);
        quoteDao.save(savedQuoteTwo);

        securityOrder = new SecurityOrder();
        securityOrder.setAccount_id(savedAccount.getId());
        securityOrder.setStatus("Created");
        securityOrder.setTicker(savedQuote.getTicker());
        securityOrder.setSize(10);
        securityOrder.setPrice(10.0);
        securityOrder.setNotes("N/A");
        securityOrder.setId(securityOrderDao.save(securityOrder).getId());

        securityOrderTwo = new SecurityOrder();
        securityOrderTwo.setAccount_id(savedAccountTwo.getId());
        securityOrderTwo.setStatus("Created");
        securityOrderTwo.setTicker(savedQuoteTwo.getTicker());
        securityOrderTwo.setSize(20);
        securityOrderTwo.setPrice(20.0);
        securityOrderTwo.setNotes("N/A");
        securityOrderTwo.setId(securityOrderDao.save(securityOrderTwo).getId());
    }

    @Test
    public void updateOne() {
        securityOrder.setStatus("Filled");
        assertEquals(1, securityOrderDao.updateOne(securityOrder));
        securityOrderTwo.setPrice(400.0);
        assertEquals(1, securityOrderDao.updateOne(securityOrderTwo));
    }

    @Test
    public void findById(){
        assertEquals(securityOrder.getTicker(), securityOrderDao.findById(securityOrder.getId()).get().getTicker());
    }

    @Test
    public void deleteById(){
        securityOrderDao.deleteById(securityOrderTwo.getId());
        assertEquals(Optional.empty(), securityOrderDao.findById(securityOrderTwo.getId()));
    }
    @After
    public void deleteAll() {
       securityOrderDao.deleteAll();
    }
}