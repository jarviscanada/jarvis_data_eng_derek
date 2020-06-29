package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.view.TraderAccountView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraderAccountService {
    private TraderDao traderDao;
    private AccountDao accountDao;
    private PositionDao positionDao;
    private SecurityOrderDao securityOrderDao;

    @Autowired
    public TraderAccountService(TraderDao traderDao, AccountDao accountDao,
                                PositionDao positionDao, SecurityOrderDao securityOrderDao) {
        this.traderDao = traderDao;
        this.accountDao = accountDao;
        this.positionDao = positionDao;
        this.securityOrderDao = securityOrderDao;
    }

    public TraderAccountView createTraderAndAccount(Trader trader) {
        /*validate user input (all fields must be non empty),
        trader cannot be null. All fields cannot be null except for id*/
        validateTrader(trader);
        //create an account with 0 amount
        trader.setId(traderDao.save(trader).getId());
        Account account = new Account();
        account.setTrader_id(trader.getId());
        account.setAmount(0d);
        account.setId(accountDao.save(account).getId());
        TraderAccountView traderAccountView = new TraderAccountView();
        traderAccountView.setAccount(account);
        traderAccountView.setTrader(trader);
        return traderAccountView;
    }

    public void deleteTraderById(Integer traderId) {
        //validate traderID
        validateTraderId(traderId);
        //get trader by traderID
        Trader trader = traderDao.findById(traderId).get();
        Account account = accountDao.findByTraderId(traderId);
        List<Position> positions = positionDao.findByColumnId("account_id", account.getId());
        if (positions.size() != 0) {
            validatePosition(positions);
        }
        validateAccountBalance(account);
        List<SecurityOrder> securityOrder = securityOrderDao.findByColumnId("account_id", account.getId());
        if (securityOrder.size() != 0) {
            for (SecurityOrder so : securityOrder) {
                securityOrderDao.deleteById(so.getId());
            }
        }
        accountDao.delete(account);
        traderDao.delete(trader);

    }

    public Account deposit(Integer traderId, Double fund) {
        validateTraderId(traderId);
        validateFund(fund);
        Account account = accountDao.findByTraderId(traderId);
        return accountDao.deposit(account, fund);
    }

    public Account withdraw(Integer traderId, Double fund) {
        validateTraderId(traderId);
        validateFund(fund);
        Account account = accountDao.findByTraderId(traderId);
        return accountDao.withdraw(account, fund);
    }

    public void validateTrader(Trader trader) {
        if ((trader.getId() != null || trader.getFirst_name() == null || trader.getLast_name() == null ||
                trader.getDob() == null || trader.getCountry() == null ||
                trader.getEmail() == null)) {
            throw new IllegalArgumentException("Error: Trader has null field or id is not null!");
        }
    }

    public void validateTraderId(Integer traderId) {
        Trader trader = traderDao.findById(traderId).get();
        if (trader == null) {
            throw new IllegalArgumentException("Trader cannot be null");
        }
    }

    public void validateAccountBalance(Account account) {
        if (account.getAmount() != 0) {
            throw new IllegalArgumentException("Trader cannot be deleted because balance is not 0. The balance is: " + account.getAmount());
        }
    }

    public void validatePosition(List<Position> positions) {
        for (Position p : positions) {
            if (p.getPosition() != 0) {
                throw new IllegalArgumentException("Error: Position is open");
            }
        }
    }

    public void validateFund(Double fund) {
        if (fund <= 0) {
            throw new IllegalArgumentException("Fund must be greater or equal to zero!");
        }
    }
}
