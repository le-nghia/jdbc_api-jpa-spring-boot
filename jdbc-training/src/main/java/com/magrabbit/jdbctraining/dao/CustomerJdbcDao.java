package com.magrabbit.jdbctraining.dao;

import com.magrabbit.jdbctraining.entity.Customer;

import java.util.List;

public interface CustomerJdbcDao {
    List<Customer> findAll();

    Customer findById(int id);

    void insert(Customer customer);

    void update(Customer customer);

    void delete(int id);

    void batchInsert(List<Customer> customers);

    void batchUpdate(List<Customer> customers);

    void batchDelete(List<Integer> ids);
}
