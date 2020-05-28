package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
        String ticker = orderDto.getTicker();
        Account account = accountDao.findById(orderDto.getAccount_id()).get();
        Quote quote = quoteDao.findById(ticker).get();

        SecurityOrder securityOrder = new SecurityOrder();
        securityOrder.setAccount_id(orderDto.getAccount_id());
        securityOrder.setStatus("CREATED");
        securityOrder.setSize(orderDto.getSize());
        securityOrder.setTicker(ticker);
        //securityOrderDao.save(securityOrder);

        if (orderDto.getSize() > 0) {
            securityOrder.setPrice(quote.getAskPrice());
            handleBuyMarketOrder(orderDto, securityOrder, account);
            quote.setAskSize(quote.getAskSize() - orderDto.getSize());
        } else if (orderDto.getSize() < 0) {
            securityOrder.setPrice(quote.getBidPrice());
            handleSellMarketOrder(orderDto, securityOrder, account);
            quote.setBidSize(quote.getBidSize() - orderDto.getSize());
        }
        securityOrder.setStatus("FILLED");
        return securityOrder;
    }

    protected void handleBuyMarketOrder(MarketOrderDto marketOrderDto, SecurityOrder securityOrder, Account account) {
        double balance = account.getAmount() - securityOrder.getSize() * securityOrder.getPrice();
        validateBalance(balance);
        account.setAmount(balance);
        accountDao.save(account);
        securityOrder.setStatus("Buy order");
        //securityOrder.setId(securityOrderDao.save(securityOrder).getId());
        securityOrderDao.save(securityOrder);
    }

    protected void handleSellMarketOrder(MarketOrderDto marketOrderDto, SecurityOrder securityOrder, Account account) {
        List<Position> positions = positionDao.findByColumnId("account_id", marketOrderDto.getAccount_id());
        if (positions == null) {
            throw new IllegalArgumentException("Improper input");
        } else {
            Position position = positions.get(0);
            if (position.getPosition() < securityOrder.getSize()) {
                throw new IllegalArgumentException("Improper input");
            } else {
                Double balance = account.getAmount() + securityOrder.getSize() * securityOrder.getPrice();
                //check position for ticker
                validatePosition(securityOrder.getTicker());
                account.setAmount(balance);
                accountDao.save(account);
                securityOrder.setStatus("Sell order");
                //securityOrder.setId(securityOrderDao.save(securityOrder).getId());
                securityOrderDao.save(securityOrder);
            }
        }
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