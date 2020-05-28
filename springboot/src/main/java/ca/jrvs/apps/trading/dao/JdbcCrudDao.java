package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Entity;
import ca.jrvs.apps.trading.model.domain.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class JdbcCrudDao<T extends Entity<Integer>> implements CrudRepository<T, Integer> {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCrudDao.class);

    abstract public JdbcTemplate getJdbcTemplate();

    abstract public SimpleJdbcInsert getSimpleJdbcInsert();

    abstract public String getTableName();

    abstract public String getIdColumnName();

    abstract Class<T> getEntityClass();

    @Override
    public void delete(T t) {
        deleteById(t.getId());
    }

    @Override
    public <S extends T> S save(S entity) {
        if (existsById(entity.getId())) {
            if (updateOne(entity) != 1) {
                throw new DataRetrievalFailureException("Unable to update quote");
            }
        } else {
            addOne(entity);
        }
        return entity;
    }

    private <S extends T> void addOne(S entity) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
        Number newId = getSimpleJdbcInsert().executeAndReturnKey(parameterSource);
        entity.setId(newId.intValue());
    }

    abstract public int updateOne(T entity);

    @Override
    public Optional<T> findById(Integer id) {
        Optional<T> entity = Optional.empty();
        String selectSql = "SELECT * FROM " + getTableName() + " WHERE " + getIdColumnName() + " =?";
        try {
            entity = Optional.ofNullable((T) getJdbcTemplate().queryForObject(selectSql,
                    BeanPropertyRowMapper.newInstance(getEntityClass()), id));
        } catch (IncorrectResultSizeDataAccessException e) {
            logger.debug("Can't find trader id:" + id, e);
        }
        return entity;
    }

    @Override
    public boolean existsById(Integer id) {
        String existsByIdSql = "SELECT COUNT (*) FROM " + getTableName() + " WHERE " + getIdColumnName() + " =?";
        boolean result = getJdbcTemplate().queryForObject(existsByIdSql, Long.class, id) > 0;
        return result;
    }

    @Override
    public List<T> findAll() {
        String findAllByIdSql = "SELECT * FROM " + getTableName();
        List<T> t = getJdbcTemplate().query(findAllByIdSql, BeanPropertyRowMapper.newInstance(getEntityClass()));
        return t;
    }

    @Override
    public List<T> findAllById(Iterable<Integer> ids) {
        List<T> t = new ArrayList<>();
        for (Integer i : ids) {
            try {
                existsById(i);
                t.add(findById(i).get());
            } catch (EmptyResultDataAccessException e) {
                logger.debug("Cannot find given ticker:" + i, e);
            }
        }
        return t;
    }
    public List<T> findByColumnId(String column, Integer id) {
        List<T> t = new ArrayList<>();
        String findByColumnIdSql = "SELECT * FROM " + getTableName() + " WHERE " + column + "=?";
        ;
        try {
            t = getJdbcTemplate().query(findByColumnIdSql, BeanPropertyRowMapper.newInstance(getEntityClass()), id);
        } catch (IncorrectResultSizeDataAccessException e) {
            logger.debug("Can't find column of the id:" + id, e);
        }
        return t;
    }
    @Override
    public long count() {
        String countSql = "SELECT COUNT(*) FROM " + getTableName();
        return getJdbcTemplate().queryForObject(countSql, Long.class);
    }

    @Override
    public void deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Ticker cannot be null!");
        }
        String deleteByIdSql = "DELETE FROM " + getTableName() + " WHERE " + getIdColumnName() + " =?";
        getJdbcTemplate().update(deleteByIdSql, id);
    }

    @Override
    public void deleteAll() {
        String deleteAllSql = "DELETE FROM " + getTableName();
        getJdbcTemplate().update(deleteAllSql);
    }
}
