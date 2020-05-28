package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.Trader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TraderDao extends JdbcCrudDao<Trader> {

    private static final Logger logger = LoggerFactory.getLogger(TraderDao.class);

    private final String TABLE_NAME = "trader";
    private final String ID_COLUMN = "id";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleInsert;

    @Autowired
    public TraderDao(DataSource dataSource) {
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
    Class<Trader> getEntityClass() {
        return Trader.class;
    }

    @Override
    public int updateOne(Trader trader) {
        String update_sql = "UPDATE trader SET first_name=?, last_name=?, dob=?, country=?, email=? WHERE id=?";
        return jdbcTemplate.update(update_sql, makeUpdateValues(trader));
    }

    private Object[] makeUpdateValues(Trader trader) {
        List<Object> list = new ArrayList<>();
        list.add(trader.getFirst_name());
        list.add(trader.getLast_name());
        list.add(trader.getDob());
        list.add(trader.getCountry());
        list.add(trader.getEmail());
        list.add(trader.getId());
        return list.toArray();
    }

    @Override
    public <S extends Trader> Iterable<S> saveAll(Iterable<S> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends Trader> entities) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
