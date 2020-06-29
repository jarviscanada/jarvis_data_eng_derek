package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class TraderAccountServiceIntTest {
    private TraderAccountView savedView;
    @Autowired
    private TraderAccountService traderAccountService;
    @Autowired
    private TraderDao traderDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private QuoteDao quoteDao;
    @Autowired
    private SecurityOrderDao securityOrderDao;
    private Account savedAccount;
    private Quote savedQuote;

    @Before
    public void setup() {
        Trader savedTrader = new Trader();
        savedTrader.setCountry("USA");
        savedTrader.setDob(new Date(1995, 10, 21));
        savedTrader.setEmail("dereksong88@yahoo.com");
        savedTrader.setFirst_name("Derek");
        savedTrader.setLast_name("Song");
        savedView = traderAccountService.createTraderAndAccount(savedTrader);
        savedAccount = traderAccountService.deposit(savedView.getTrader().getId(), 100.0);
    }

    @Test
    public void withdraw() {
        Account savedAccountTwo = new Account();
        savedAccountTwo = traderAccountService.withdraw(savedView.getTrader().getId(), 100.0);
        assertEquals((Double) 0.0, savedAccountTwo.getAmount());
    }


    @Test
    public void deleteTraderById() {
        savedQuote = new Quote();
        savedQuote.setAskPrice(10.0);
        savedQuote.setAskSize(10);
        savedQuote.setBidPrice(11.0);
        savedQuote.setBidSize(11);
        savedQuote.setTicker("GOOGL");
        savedQuote.setLastPrice(22.0);
        quoteDao.save(savedQuote);

        SecurityOrder securityOrder = new SecurityOrder();
        securityOrder.setAccount_id(savedView.getAccount().getId());
        securityOrder.setStatus("Created");
        securityOrder.setTicker(savedQuote.getTicker());
        securityOrder.setSize(10);
        securityOrder.setPrice(10.0);
        securityOrder.setNotes("N/A");
        Integer id = securityOrderDao.save(securityOrder).getId();
        securityOrder.setId(id);
        savedAccount.setAmount(0.0);
        accountDao.save(savedAccount);
        traderAccountService.deleteTraderById(savedView.getTrader().getId());
        assertEquals(Optional.empty(), securityOrderDao.findById(id));
        assertEquals(Optional.empty(), accountDao.findById(1));
        assertEquals(Optional.empty(), traderDao.findById(1));
    }
}