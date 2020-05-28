package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceUnitTest {
    //capture parameter when calling securityOrderDao.save
    @Captor
    ArgumentCaptor<SecurityOrder> captorSecurityOrder;

    //mock all dependencies
    @Mock
    private AccountDao accountDao;
    @Mock
    private SecurityOrderDao securityOrderDao;
    @Mock
    private QuoteDao quoteDao;
    @Mock
    private PositionDao positionDao;

    //injecting mocked dependencies to the testing class via constructor
    @InjectMocks
    private OrderService orderService;

    @Before
    public void setup() {
        Quote quote = new Quote();
        quote.setBidSize(2);
        quote.setAskSize(2);
        quote.setBidPrice(10.0);
        quote.setAskPrice(11.0);
        quote.setTicker("FB");
        quote.setLastPrice(10.0);
        Mockito.when(quoteDao.findById("FB")).thenReturn(Optional.of(quote));

        Account account = new Account();
        account.setAmount(1000.0);
        account.setId(1);
        account.setTrader_id(2);
        Mockito.when(accountDao.findById(any())).thenReturn(Optional.of(account));

        List<Position> positions = new ArrayList<>();
        Position position = new Position();
        position.setPosition(5);
        positions.add(position);
        when(positionDao.findByColumnId(any(), any())).thenReturn(positions);

        Account accountWithdraw = new Account();
        accountWithdraw.setAmount(800.0);
        accountWithdraw.setTrader_id(5);
        accountWithdraw.setId(2);
        when(accountDao.save(any())).thenReturn(accountWithdraw);

        SecurityOrder securityOrder = new SecurityOrder();
        securityOrder.setStatus("Done");
        securityOrder.setId(2);
        securityOrder.setAccount_id(1);
        securityOrder.setSize(9);
        securityOrder.setTicker("FB");
        securityOrder.setPrice(12.0);
        when(securityOrderDao.save(any())).thenReturn(securityOrder);
    }

    @Test
    public void buy() {
        MarketOrderDto marketOrderDto = new MarketOrderDto();
        marketOrderDto.setAccount_id(1);
        marketOrderDto.setSize(5);
        marketOrderDto.setTicker("FB");
        SecurityOrder securityOrderWithdraw = orderService.executeMarketOrder(marketOrderDto);
        verify(securityOrderDao).save(captorSecurityOrder.capture());
        assertEquals("FB", captorSecurityOrder.getValue().getTicker());
        assertEquals("Filled", captorSecurityOrder.getValue().getStatus());
    }

    @Test
    public void sell() {
        MarketOrderDto marketOrderDto = new MarketOrderDto();
        marketOrderDto.setAccount_id(2);
        marketOrderDto.setSize(-5);
        marketOrderDto.setTicker("FB");
        SecurityOrder securityOrderOut = orderService.executeMarketOrder(marketOrderDto);
        verify(securityOrderDao).save(captorSecurityOrder.capture());
        assertEquals("FB", captorSecurityOrder.getValue().getTicker());
        assertEquals("Filled", captorSecurityOrder.getValue().getStatus());
    }
}
