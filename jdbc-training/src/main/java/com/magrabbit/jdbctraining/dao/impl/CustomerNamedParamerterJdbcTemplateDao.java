package com.magrabbit.jdbctraining.dao.impl;

import com.magrabbit.jdbctraining.dao.CustomerJdbcDao;
import com.magrabbit.jdbctraining.entity.Customer;
import com.magrabbit.jdbctraining.mapper.CustomerRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerNamedParamerterJdbcTemplateDao implements CustomerJdbcDao {

    private static final String SQL_FIND_ALL = "SELECT id, name, email FROM customers";
    private static final String SQL_FIND_BY_ID = "SELECT id, name, email FROM customers WHERE id = :id";
    private static final String SQL_INSERT = "INSERT INTO customers (name, email) VALUES (:name, :email)";
    private static final String SQL_UPDATE = "UPDATE customers SET name = :name, email = :email WHERE id = :id";
    private static final String SQL_DELETE = "DELETE FROM customers WHERE id = :id";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CustomerNamedParamerterJdbcTemplateDao(
            NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Customer> findAll() {
        return namedParameterJdbcTemplate.query(SQL_FIND_ALL, new CustomerRowMapper());
    }

    @Override
    public Customer findById(int id) {
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource().addValue("id", id);
        List<Customer> results =
                namedParameterJdbcTemplate.query(SQL_FIND_BY_ID, sqlParameterSource, new CustomerRowMapper());
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public void insert(Customer customer) {
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource()
                        .addValue("name", customer.getName())
                        .addValue("email", customer.getEmail());
        namedParameterJdbcTemplate.update(SQL_INSERT, sqlParameterSource);
    }

    @Override
    public void update(Customer customer) {
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource()
                        .addValue("name", customer.getName())
                        .addValue("email", customer.getEmail())
                        .addValue("id", customer.getId());
        namedParameterJdbcTemplate.update(SQL_UPDATE, sqlParameterSource);
    }

    @Override
    public void delete(int id) {
        SqlParameterSource sqlParameterSource =
                new MapSqlParameterSource().addValue("id", id);
        namedParameterJdbcTemplate.update(SQL_DELETE, sqlParameterSource);
    }

    @Override
    public void batchInsert(List<Customer> customers) {
        List<SqlParameterSource> sqlParameterSourceList = new ArrayList<>();
        for (Customer customer : customers) {
            sqlParameterSourceList.add(new MapSqlParameterSource()
                    .addValue("name", customer.getName())
                    .addValue("email", customer.getEmail()));
        }
        namedParameterJdbcTemplate.batchUpdate(SQL_INSERT, sqlParameterSourceList.toArray(new SqlParameterSource[0]));
    }

    @Override
    public void batchUpdate(List<Customer> customers) {
        List<SqlParameterSource> sqlParameterSourceList = new ArrayList<>();
        for (Customer customer : customers) {
            sqlParameterSourceList.add(new MapSqlParameterSource()
                    .addValue("name", customer.getName())
                    .addValue("email", customer.getEmail())
                    .addValue("id", customer.getId()));
        }
        namedParameterJdbcTemplate.batchUpdate(SQL_UPDATE, sqlParameterSourceList.toArray(new SqlParameterSource[0]));
    }

    @Override
    public void batchDelete(List<Integer> ids) {
        List<SqlParameterSource> sqlParameterSourceList = new ArrayList<>();
        for (Integer id : ids) {
            sqlParameterSourceList.add(new MapSqlParameterSource().addValue("id", id));
        }
        namedParameterJdbcTemplate.batchUpdate(SQL_DELETE, sqlParameterSourceList.toArray(new SqlParameterSource[0]));
    }
}
