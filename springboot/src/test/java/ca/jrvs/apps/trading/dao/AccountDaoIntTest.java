package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
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

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class AccountDaoIntTest {
    @Autowired
    TraderDao traderDao;
    @Autowired
    private AccountDao accountDao;
    private Account savedAccount;
    private Account savedAccountTwo;
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
        savedAccount.setTrader_id(1);
        savedAccount.setAmount(100.0);
        accountDao.save(savedAccount);
        //savedAccount.setId(accountDao.save(savedAccount).getId());

        savedAccountTwo = new Account();
        savedAccountTwo.setTrader_id(2);
        savedAccountTwo.setAmount(200.0);
        accountDao.save(savedAccountTwo);
        //savedAccountTwo.setId(accountDao.save(savedAccountTwo).getId());
    }

    @Test
    public void findById() {
        assertEquals(savedAccount.getAmount(), accountDao.findById(1).get().getAmount());
       /* System.out.println("Expected:" + savedTrader.getFirst_name());
        System.out.println("Actual:" + traderDao.findById(1).get().getFirst_name());*/
        assertEquals(savedAccountTwo.getAmount(), accountDao.findById(2).get().getAmount());
        /*System.out.println("Expected:" + savedTraderTwo.getFirst_name());
        System.out.println("Actual:" + traderDao.findById(2).get().getFirst_name());*/
    }

    @Test
    public void updateOne() {
        savedAccount.setAmount(10.0);
        savedAccountTwo.setAmount(20.0);
        assertEquals(1, accountDao.updateOne(savedAccount));
        assertEquals(1, accountDao.updateOne(savedAccountTwo));
    }

    @Test
    public void findByTraderId() {
        assertEquals(savedAccount.getAmount(), accountDao.findByTraderId(1).getAmount());
        assertEquals(savedAccountTwo.getAmount(), accountDao.findByTraderId(2).getAmount());
    }

    @Test
    public void deposit() {
        Account newAccount;
        Double balance = savedAccount.getAmount() + 10.0;
        newAccount = accountDao.deposit(savedAccount, +10.0);
        assertEquals(balance, newAccount.getAmount());

        //Test when fund is less than zero
//        Account newAccountTwo;
//        Double balanceTwo = savedAccount.getAmount() - 10.0;
//        newAccount = accountDao.deposit(savedAccount,-10.0);
//        assertEquals(balance, newAccount.getAmount());
    }

    @Test
    public void withdraw() {
        Account withdrawAccount;
        Double newbalance = savedAccount.getAmount() - 10;
        withdrawAccount = accountDao.withdraw(savedAccount, 10.0);
        assertEquals(newbalance, withdrawAccount.getAmount());

        //Test when the fund is less than zero
//        Account withdrawAccountTwo;
//        Double newbalanceTwo = savedAccount.getAmount() + 10;
//        withdrawAccountTwo = accountDao.withdraw(savedAccount, -10.0);
//        assertEquals(newbalanceTwo,withdrawAccountTwo.getAmount());
    }

    @Test
    public void deleteById() {
        accountDao.deleteById(1);
        assertEquals(Optional.empty(), accountDao.findById(1));

    }

    @After
    public void deleteOne() {
        accountDao.deleteAll();
    }
}