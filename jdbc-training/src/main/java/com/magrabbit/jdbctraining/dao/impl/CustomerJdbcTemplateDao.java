package com.magrabbit.jdbctraining.dao.impl;

import com.magrabbit.jdbctraining.dao.CustomerJdbcDao;
import com.magrabbit.jdbctraining.entity.Customer;
import com.magrabbit.jdbctraining.mapper.CustomerRowMapper;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CustomerJdbcTemplateDao implements CustomerJdbcDao {

    private static final String SQL_FIND_ALL = "SELECT id, name, email FROM customers";
    private static final String SQL_FIND_BY_ID = "SELECT id, name, email FROM customers WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO customers (name, email) VALUES (?, ?)";
    private static final String SQL_UPDATE = "UPDATE customers SET name = ?, email = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM customers WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    public CustomerJdbcTemplateDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, new CustomerRowMapper());
    }

    @Override
    public Customer findById(int id) {
        List<Customer> results = jdbcTemplate.query(SQL_FIND_BY_ID, new CustomerRowMapper(), id);
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public void insert(Customer customer) {
        jdbcTemplate.update(SQL_INSERT, preparedStatement -> {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEmail());
        });
    }

    @Override
    public void update(Customer customer) {
        jdbcTemplate.update(SQL_UPDATE, preparedStatement -> {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setInt(3, customer.getId());
        });
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update(SQL_DELETE, preparedStatement -> preparedStatement.setInt(1, id));
    }

    @Override
    public void batchInsert(List<Customer> customers) {
        jdbcTemplate.batchUpdate(SQL_INSERT, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1, customers.get(i).getName());
                preparedStatement.setString(2, customers.get(i).getEmail());
            }

            @Override
            public int getBatchSize() {
                return customers.size();
            }
        });
    }

    @Override
    public void batchUpdate(List<Customer> customers) {
        jdbcTemplate.batchUpdate(SQL_UPDATE, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1, customers.get(i).getName());
                preparedStatement.setString(2, customers.get(i).getEmail());
                preparedStatement.setInt(3, customers.get(i).getId());
            }

            @Override
            public int getBatchSize() {
                return customers.size();
            }
        });
    }

    @Override
    public void batchDelete(List<Integer> ids) {
        jdbcTemplate.batchUpdate(SQL_DELETE, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setInt(1, ids.get(i));
            }

            @Override
            public int getBatchSize() {
                return ids.size();
            }
        });
    }
}
