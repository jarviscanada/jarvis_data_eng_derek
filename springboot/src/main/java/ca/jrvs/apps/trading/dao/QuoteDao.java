package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public class QuoteDao implements CrudRepository<Quote, String> {
    private static final String TABLE_NAME = "quote";
    private static final String ID_COLUMN_NAME = "ticker";
    private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    //private String[] tickerFileds = {"GOOGL", "FB", "AAPL", "MMM", "AMD", "APA"};

    @Autowired
    public QuoteDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
    }

    public static void main(String[] args) {
//        String url = "jdbc:postgresql://localhost:5432/jrvstrading";
//        String user = "postgres";
//        String password = "password";
        System.out.println("Creating apacheDataSource");
        String url = System.getenv("PSQL_URL");
        String user = System.getenv("PSQL_USER");
        String password = System.getenv("PSQL_PASSWORD");
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(url);
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(password);
        QuoteDao quoteDao = new QuoteDao(basicDataSource);

        Quote saveQuote = new Quote();
        saveQuote.setTicker("70M");
        saveQuote.setLastPrice(30.1);
        saveQuote.setBidPrice(10.2);
        saveQuote.setBidSize(10);
        saveQuote.setAskPrice(20.0);
        saveQuote.setAskSize(20);
        quoteDao.save(saveQuote);
        List<Quote> allQuotes = quoteDao.findAll();
        for (int i = 0; i < allQuotes.size(); i++) {
            System.out.println(allQuotes.get(i).getTicker());
        }
    }

    @Override
    public Quote save(Quote quote) {
        if (existsById(quote.getTicker())) {
            int updatedRowNo = updateOne(quote);
            if (updatedRowNo != 1) {
                throw new DataRetrievalFailureException("Unable to update quote");
            }
        } else {
            addOne(quote);
        }
        return quote;
    }

    private void addOne(Quote quote) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(quote);
        int row = simpleJdbcInsert.execute(parameterSource);
        if (row != 1) {
            throw new IncorrectResultSizeDataAccessException("Failed to insert", 1, row);
        }
    }

    private int updateOne(Quote quote) {
        String update_sql = "UPDATE quote SET last_price=?, bid_price=?, bid_size=?, ask_price=?, ask_size=? WHERE ticker=?";
        return jdbcTemplate.update(update_sql, makeUpdateValues(quote));
    }

    private Object[] makeUpdateValues(Quote quote) {
        List<Object> list = new ArrayList<>();
        list.add(quote.getLastPrice());
        list.add(quote.getBidPrice());
        list.add(quote.getBidSize());
        list.add(quote.getAskPrice());
        list.add(quote.getAskSize());
        list.add(quote.getTicker());
        return list.toArray();
    }

    @Override
    public <S extends Quote> List<S> saveAll(Iterable<S> quotes) {
        List<S> listQuote = new ArrayList<S>();
        for (Quote q : quotes) {
            listQuote.add((S) save(q));
        }
        return listQuote;
    }

    @Override
    public Optional<Quote> findById(String ticker) {
        Optional<Quote> optionalQuote;
        Quote findByIdQuote = null;
        String findByIdSql = "SELECT * FROM " + TABLE_NAME + " WHERE ticker =?";
        try {
            findByIdQuote = jdbcTemplate.queryForObject(findByIdSql, BeanPropertyRowMapper.newInstance(Quote.class), ticker);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("Cannot find given ticker:" + ticker, e);
        }
        return optionalQuote = Optional.of(findByIdQuote);
    }

    @Override
    public boolean existsById(String ticker) {
        //It must be COUNT (*)
        String existsByIdSql = "SELECT COUNT (*) FROM " + TABLE_NAME + " WHERE ticker =?";
        boolean result = jdbcTemplate.queryForObject(existsByIdSql, Integer.class, ticker) > 0;
        return result;
    }

    @Override
    public List<Quote> findAll() {
        String findAllSql = "SELECT * FROM " + TABLE_NAME;
        List<Quote> listQuote = jdbcTemplate.query(findAllSql, BeanPropertyRowMapper.newInstance(Quote.class));
        return listQuote;
    }

    @Override
    public Iterable<Quote> findAllById(Iterable<String> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public long count() {
        String countSql = "SELECT COUNT(*) FROM " + TABLE_NAME;
        return jdbcTemplate.queryForObject(countSql, Long.class);
    }

    @Override
    public void deleteById(String s) {
        if (s == null) {
            throw new IllegalArgumentException("Ticker cannot be null!");
        }
        String deleteByIdSql = "DELETE FROM " + TABLE_NAME + " WHERE ticker =?";
        jdbcTemplate.update(deleteByIdSql, s);
    }

    @Override
    public void delete(Quote quote) {
        deleteById(quote.getId());
    }

    @Override
    public void deleteAll(Iterable<? extends Quote> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll() {
        String deleteAllSql = "DELETE FROM " + TABLE_NAME;
        jdbcTemplate.update(deleteAllSql);
    }

}

