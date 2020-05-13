package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.PortfolioView;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
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
    public DashboardService(TraderDao traderDao, PositionDao positionDao, AccountDao accountDao, QuoteDao quoteDao) {
        this.traderDao = traderDao;
        this.positionDao = positionDao;
        this.accountDao = accountDao;
        this.quoteDao = quoteDao;
    }

    public TraderAccountView getTraderAccount(Integer traderId) {
        validateTraderID(traderId);
        Trader trader = traderDao.findById(traderId).get();
        Account account = findAccountByTraderId(traderId);
        TraderAccountView traderAccountView = new TraderAccountView();
        traderAccountView.setTrader(trader);
        traderAccountView.setAccount(account);
        return traderAccountView;
    }

    public PortfolioView getProfileViewByTraderId(Integer traderId) {
        validateTraderID(traderId);
        Account account = findAccountByTraderId(traderId);
        Position position = positionDao.findById(account.getId()).get();.
        PortfolioView portfolioView = new PortfolioView();
        portfolioView.setAccount(account);
        portfolioView.setPosition(position);
        return portfolioView;
    }

    private Account findAccountByTraderId(Integer traderId) {
        Account account;
        try {
            account = accountDao.findByTraderId(traderId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid traderId");
        }
        return account;
    }

    public void validateTraderID(Integer traderId) {
        if (traderId == null) {
            throw new IllegalArgumentException("TraderID cannot be null");
        }
    }
}
