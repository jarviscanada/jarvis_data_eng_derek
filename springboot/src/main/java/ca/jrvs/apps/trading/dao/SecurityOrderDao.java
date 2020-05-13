package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.SecurityOrder;
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
public class SecurityOrderDao extends JdbcCrudDao<SecurityOrder> {
    private static final Logger logger = LoggerFactory.getLogger(TraderDao.class);

    private final String TABLE_NAME = "security_order";
    private final String ID_COLUMN = "id";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleInsert;

    @Autowired
    public SecurityOrderDao(DataSource dataSource) {
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
    Class<SecurityOrder> getEntityClass() {
        return SecurityOrder.class;
    }

    @Override
    public int updateOne(SecurityOrder securityOrder) {
        String updateSecurityOrderSql = "UPDATE security_order SET status=?, size=?, price=?, notes =? WHERE ticker =?";
        return jdbcTemplate.update(updateSecurityOrderSql, makeUpdateValues(securityOrder));
    }

    private Object[] makeUpdateValues(SecurityOrder securityOrder) {
        List list = new ArrayList();
        list.add(securityOrder.getStatus());
        list.add(securityOrder.getSize());
        list.add(securityOrder.getPrice());
        list.add(securityOrder.getNotes());
        Object[] obj = list.toArray();
        return obj;
    }

    @Override
    public <S extends SecurityOrder> Iterable<S> saveAll(Iterable<S> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends SecurityOrder> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
