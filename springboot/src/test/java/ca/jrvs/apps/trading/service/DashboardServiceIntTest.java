/*package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.*;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DashboardServiceUnitTest {
    //capture parameter when calling securityOrderDao.save
    @Captor
    ArgumentCaptor<SecurityOrder> captorSecurityOrder;

    //mock all dependencies
    @Mock
    private AccountDao accountDao;
    @Mock
    private TraderDao traderDao;
    @Mock
    private QuoteDao quoteDao;
    @Mock
    private PositionDao positionDao;

    //injecting mocked dependencies to the testing class via constructor
    @InjectMocks
    private DashboardService dashboardService;*/

package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.PortfolioView;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class DashboardServiceIntTest {
    Position position = new Position();
    Trader savedTrader = new Trader();
    Account account = new Account();
    private TraderAccountView savedView;
    @Autowired
    private DashboardService dashboardService;
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
    private Quote quote = new Quote();
    private List<Position> positions = new ArrayList<>();

    @Before
    public void setup() {
//        quote.setBidSize(2);
//        quote.setAskSize(2);
//        quote.setBidPrice(10.0);
//        quote.setAskPrice(11.0);
//        quote.setTicker("FB");
//        quote.setLastPrice(10.0);
//        quoteDao.save(quote);

//        List<Position> positions = new ArrayList<>();
//        Position position = new Position();
        position.setPosition(5);
        position.setTicker("FB");
        position.setAccount_id(1);
        positions.add(position);

        savedTrader.setCountry("USA");
        savedTrader.setDob(new Date(1995, 10, 21));
        savedTrader.setEmail("dereksong88@yahoo.com");
        savedTrader.setFirst_name("Derek");
        savedTrader.setLast_name("Song");
        traderDao.save(savedTrader);
        Integer traderId = traderDao.save(savedTrader).getId();

        account.setAmount(1000.0);
        account.setTrader_id(traderId);
        accountDao.save(account);

    }

    @Test
    public void getTraderAccount() {
        TraderAccountView traderAccountView = dashboardService.getTraderAccount(1);
        assertEquals((Double) 1000.0, traderAccountView.getAccount().getAmount());
        assertEquals("USA", traderAccountView.getTrader().getCountry());

    }

    @Test
    public void getProfileViewByTraderId() {
        PortfolioView portfolioView = dashboardService.getProfileViewByTraderId(1);
        assertEquals(account.getAmount(), portfolioView.getAccount().getAmount());
    }
}
