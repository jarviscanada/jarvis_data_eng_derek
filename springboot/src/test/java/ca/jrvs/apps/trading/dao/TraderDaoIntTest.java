package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Trader;
import org.assertj.core.util.Lists;
import org.junit.After;
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
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class TraderDaoIntTest {
    @Autowired
    private TraderDao traderDao;
    private Trader savedTrader;
    private Trader savedTraderTwo;

    @Before
    public void insert() {
        savedTrader = new Trader();
        savedTrader.setCountry("USA");
        savedTrader.setDob(new Date(1999, 03, 11));
        savedTrader.setEmail("dereksong88@yahoo.com");
        savedTrader.setFirst_name("Derek");
        savedTrader.setLast_name("Song");
        savedTrader.setId(traderDao.save(savedTrader).getId());

        savedTraderTwo = new Trader();
        savedTraderTwo.setCountry("Canada");
        savedTraderTwo.setDob(new Date(1999, 03, 11));
        savedTraderTwo.setEmail("johnsong88@yahoo.com");
        savedTraderTwo.setFirst_name("John");
        savedTraderTwo.setLast_name("Song");
        savedTraderTwo.setId(traderDao.save(savedTraderTwo).getId());
    }

    @Test
    public void findById() {
        assertEquals(savedTrader.getFirst_name(), traderDao.findById(1).get().getFirst_name());
        assertEquals(savedTraderTwo.getFirst_name(), traderDao.findById(2).get().getFirst_name());
    }

    @Test
    public void findAllById() {
        List<Trader> traders = Lists.newArrayList(traderDao.findAllById(Arrays.asList(savedTrader.getId())));
        assertEquals(1, traders.size());
        assertEquals(savedTrader.getCountry(), traders.get(0).getCountry());
        assertEquals(savedTrader.getEmail(), traders.get(0).getEmail());
        assertEquals(savedTrader.getFirst_name(), traders.get(0).getFirst_name());
        assertEquals(savedTrader.getLast_name(), traders.get(0).getLast_name());
        assertEquals(savedTrader.getDob(), traders.get(0).getDob());
    }

    @Test
    public void updateOne() {
        savedTraderTwo.setEmail("johnsong22@yahoo.com");
        assertEquals(1, traderDao.updateOne(savedTraderTwo));
    }

    @Test
    public void deleteById() {
        traderDao.deleteById(1);
        assertEquals(Optional.empty(), traderDao.findById(1));

    }

    @After
    public void deleteAll() {
        traderDao.deleteAll();
    }
}