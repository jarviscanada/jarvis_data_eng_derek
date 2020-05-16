package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.MarketOrderDto;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private AccountDao accountDao;
    private SecurityOrderDao securityOrderDao;
    private QuoteDao quoteDao;
    private PositionDao positionDao;

    @Autowired
    public OrderService(AccountDao accountDao, SecurityOrderDao securityOrderDao,
                        QuoteDao quoteDao, PositionDao positionDao) {
        this.accountDao = accountDao;
        this.securityOrderDao = securityOrderDao;
        this.quoteDao = quoteDao;
        this.positionDao = positionDao;
    }

    public SecurityOrder executeMarketOrder(MarketOrderDto orderDto) {
        validateOrder(orderDto);
        //create a securityOrder
        SecurityOrder securityOrder = new SecurityOrder();
        securityOrder.setAccount_id(orderDto.getAccount_id());
        securityOrder.setSize(orderDto.getSize());
        securityOrder.setTicker(orderDto.getTicker());
        securityOrderDao.save(securityOrder);
        Account account = accountDao.findById(orderDto.getAccount_id()).get();
        if (orderDto.getSize() > 0) {
            handleBuyMarketOrder(orderDto, securityOrder, account);
        } else if (orderDto.getSize() < 0) {
            handleSellMarketOrder(orderDto, securityOrder, account);
        }
        return securityOrder;
    }

    protected void handleBuyMarketOrder(MarketOrderDto marketOrderDto, SecurityOrder securityOrder, Account account) {
        double balance = account.getAmount() - securityOrder.getSize() * securityOrder.getPrice();
        validateBalance(balance);
        account.setAmount(balance);
        securityOrder.setStatus("Buy order");
        securityOrderDao.save(securityOrder);
    }

    protected void handleSellMarketOrder(MarketOrderDto marketOrderDto, SecurityOrder securityOrder, Account account) {
        Double balance = account.getAmount() + securityOrder.getSize() * securityOrder.getPrice();
        //check position for ticker
        validatePosition(securityOrder.getTicker());
        account.setAmount(balance);
        securityOrder.setStatus("Sell order");
        securityOrderDao.save(securityOrder);
    }

    public void validateOrder(MarketOrderDto orderDto) {
        if (orderDto.getSize() == 0 || orderDto.getTicker() == null) {
            throw new IllegalArgumentException("Order is invalid!");
        }
    }

    public void validateBalance(Double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("You do not have enough balance!");
        }
    }

    public void validatePosition(String ticker) {
        if (ticker == null) {
            throw new IllegalArgumentException("Error: Ticker is null.");
        }
    }
}