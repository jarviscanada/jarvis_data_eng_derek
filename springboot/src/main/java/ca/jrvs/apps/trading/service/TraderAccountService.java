package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.TraderDao;
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
                                PositionDao positionDao, SecurityOrderDao securityOrderDao){
        this.traderDao = traderDao;
        this.accountDao = accountDao;
        this.positionDao = positionDao;
        this.securityOrderDao = securityOrderDao;
    }

    public TraderAccountView createTraderAndAccount(Trader trader){}

    public void deleteTraderById(Integer traderId){}

    public Account deposit(Integer traderId, Double fund){}

    public Account withdraw(Integer traderId, Double fund){}


}
