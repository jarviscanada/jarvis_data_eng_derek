package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DashboardService {
    private TraderDao traderDao;
    private PositionDao positionDao;
    private AccountDao accountDao;
    private QuoteDao quoteDao;

    @Autowired
    public DashboardService(TraderDao traderDao, PositionDao positionDao, AccountDao accountDao, QuoteDao quoteDao){
        this.traderDao = traderDao;
        this.positionDao = positionDao;
        this.accountDao = accountDao;
        this.quoteDao = quoteDao;
    }
    public TraderAccountView getTraderAccount(Integer traderId){}

    public PortfolioView getProfileViewByTraderId(Integer traderId){}

    private Account findAccountByTraderId(Integer traderId){
        return accountDao.findByTraderId(traderId).orElseThrow(() -> new IllegalArgumentException("Invalid traderId"));
    }
}
