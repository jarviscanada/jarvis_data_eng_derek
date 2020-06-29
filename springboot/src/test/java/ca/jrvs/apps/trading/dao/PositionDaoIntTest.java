package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.*;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class PositionDaoIntTest {
    @Autowired
    PositionDao positionDao;
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
    private Position position;
    private Position positionTwo;
    private Position positionThree;

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

        savedAccount = new Account();
        savedAccount.setTrader_id(savedTrader.getId());
        savedAccount.setAmount(100.0);
        savedAccount.setId(accountDao.save(savedAccount).getId());

        savedQuote = new Quote();
        savedQuote.setAskPrice(10.0);
        savedQuote.setAskSize(10);
        savedQuote.setBidPrice(11.0);
        savedQuote.setBidSize(11);
        savedQuote.setTicker("GOOGL");
        savedQuote.setLastPrice(22.0);
        quoteDao.save(savedQuote);

        securityOrder = new SecurityOrder();
        securityOrder.setAccount_id(savedAccount.getId());
        securityOrder.setStatus("Created");
        securityOrder.setTicker(savedQuote.getTicker());
        securityOrder.setSize(10);
        securityOrder.setPrice(10.0);
        securityOrder.setNotes("N/A");
        securityOrder.setId(securityOrderDao.save(securityOrder).getId());

        position = new Position();
        position.setAccount_id(savedAccount.getId());
        position.setTicker(savedQuote.getTicker());
        position.setPosition(securityOrder.getSize());
    }


    @Test
    public void findAllById() {
        List<Position> positions = Lists.newArrayList(positionDao.findAllById(Arrays.asList(savedAccount.getId())));
        assertEquals(1, positions.size());
    }

}