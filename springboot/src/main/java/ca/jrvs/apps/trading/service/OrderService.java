package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.QuoteDao;
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
        public SecurityOrder executeMarketOrder (MarketOrderDto orderDto){
        }

        protected void handleBuyMarketOrder (MarketOrderDto marketOrderDto,
                SecurityOrder securityOrder, Account account){
        }

        protected void handleSellMarketOrder (MarketOrderDto marketOrderDto,
                SecurityOrder securityOrder, Account account){
        }
    }