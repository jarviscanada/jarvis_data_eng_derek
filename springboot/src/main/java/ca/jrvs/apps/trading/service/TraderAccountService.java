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
        //validate user input (all fields must be non empty),
        //trader cannot be null. All fields cannot be null except for id
        validateTrader(trader);
        //create an account with 0 amount
        Account account = new Account();
        account.setId(accountDao.save(account).getId());
        account.setTrader_id(trader.getId());
        account.setAmount(0d);
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
        Account account = accountDao.findById(traderId).get();
        Position position = positionDao.findById(traderId).get();
        //check account balance
        validateAccountBalance(account);
        //how  to check position
        validatePosition(position);
        SecurityOrder securityOrder = securityOrderDao.findById(traderId).get();
        traderDao.delete(trader);
        accountDao.delete(account);
        securityOrderDao.delete(securityOrder);
    }

    public Account deposit(Integer traderId, Double fund) {
        validateTraderId(traderId);
        Account account = accountDao.findById(traderId).get();
        return accountDao.updateAmount(account, fund);
    }

    public Account withdraw(Integer traderId, Double fund) {
        validateTraderId(traderId);
        Account account = accountDao.findById(traderId).get();
        return accountDao.withdraw(account,fund);
    }

    public void validateTrader(Trader trader) {
        if ((trader.getId() != null || trader.getFirst_name() == null || trader.getLast_name() == null ||
                trader.getDob() == null || trader.getCountry() == null ||
                trader.getEmail() == null)) {
            throw new IllegalArgumentException("Error: Trader has null field or id is not null!");
        }
    }

    public void validateTraderId(Integer traderId) {
        if (traderId == null) {
            throw new IllegalArgumentException("TraderID cannot be null");
        }
    }

    public void validateAccountBalance(Account account) {
        if (account.getAmount() != 0) {
            throw new IllegalArgumentException("Trader cannot be deleted because balance is not 0.");
        }
    }

    public void validatePosition(Position position) {
        if (position.getPosition() != 0) {
            throw new IllegalArgumentException("Error: Position is open");
        }
    }


}
