package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountDao extends JdbcCrudDao<Account> {
    private static final Logger logger = LoggerFactory.getLogger(TraderDao.class);

    private final String TABLE_NAME = "account";
    private final String ID_COLUMN = "id";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleInsert;

    @Autowired
    public AccountDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME).usingGeneratedKeyColumns(ID_COLUMN);
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public SimpleJdbcInsert getSimpleJdbcInsert() {
        return simpleInsert;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getIdColumnName() {
        return ID_COLUMN;
    }

    @Override
    Class<Account> getEntityClass() {
        return Account.class;
    }

    @Override
    public int updateOne(Account account) {
        String update_sql = "UPDATE " + TABLE_NAME + " SET amount = ? WHERE id=?";
        return jdbcTemplate.update(update_sql, makeUpdateValues(account));
    }

    private Object[] makeUpdateValues(Account account) {
        List<Object> list = new ArrayList<>();
        list.add(account.getAmount());
        list.add(account.getId());
        return list.toArray();
    }

    @Override
    public <S extends Account> Iterable<S> saveAll(Iterable<S> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends Account> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public Account deposit(Account account, Double fund) {
        if (fund < 0) {
            throw new IllegalArgumentException("Fund cannot be less than 0!");
        }
        Double newAmount = account.getAmount() + fund;
        account.setAmount(newAmount);
        updateOne(account);
        return account;
    }

    public Account withdraw(Account account, Double fund) {
        if (fund < 0) {
            throw new IllegalArgumentException("Fund cannot be less than 0!");
        }
        Double balance = account.getAmount() - fund;
        if (balance < 0) {
            throw new IllegalArgumentException("You do have enough money!");
        } else {
            account.setAmount(balance);
           updateOne(account);
            return account;
        }
    }

    public Account findByTraderId(Integer trader_id) {
        String selectSql = "SELECT * FROM " + getTableName() + " WHERE " + "trader_id" + " =?";
        Account account = new Account();
        try {
            account = getJdbcTemplate().queryForObject(selectSql, BeanPropertyRowMapper.newInstance(getEntityClass()), trader_id);
            ;
        } catch (IncorrectResultSizeDataAccessException e) {
            logger.debug("Can't find trader id:" + trader_id, e);
        }
        return account;
    }
}
