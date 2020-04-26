package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import org.aspectj.apache.bcel.generic.TABLESWITCH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class QuoteDao implements CrudRepository<Quote, String> {
    private static final String TABLE_NAME = "quote";
    private static final String ID_COLUMN_NAME = "ticker";
    private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public QuoteDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
    }


    @Override
    public Quote save(Quote quote) {
        if(existsById(quote.getTicker())){
            int updatedRowNo = updateOne(quote);
            if(updatedRowNo!=1){
                throw new DataRetrievalFailureException("Unable to update quote");
            }
        } else{
            addOne(quote);
        }
        return quote;
    }

    private void addOne(Quote quote){
        Object object;
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(quote);
        int row = simpleJdbcInsert.execute(parameterSource);
        if(row !=1){
            throw new IncorrectResultSizeDataAccessException("Failed to insert", 1, row);
        }
    }
    private int updateOne(Quote quote){
        String update_sql = "UPDATE quote SET last_price=?, bid_price=?, bid_size=?, ask_price=?, ask_size=? WHERE ticker =?";
        return jdbcTemplate.update(update_sql, makeUpdateValues(quote));
    }
    private Object[] makeUpdateValues(Quote quote){
return null;
//
    }

    @Override
    public <S extends Quote> List<S> saveAll(Iterable<S> quotes) {
        return null;
        //
    }

    @Override
    public Optional<Quote> findById(String ticker) {
return null;
//
    }

    @Override
    public boolean existsById(String ticker) {
        return false;
    }

    @Override
    public List<Quote> findAll() {
return null;
    }

    @Override
    public Iterable<Quote> findAllById(Iterable<String> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(Quote quote) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends Quote> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll() {

    }
}
