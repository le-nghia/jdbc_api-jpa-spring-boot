package com.magrabbit.jdbctraining.service;

import com.magrabbit.jdbctraining.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAll();

    Customer getById(int id);

    void save(Customer customer);

    void delete(int id);

    void bulkSave(List<Customer> customers);

    void bulkDelete(List<Integer> ids);
}
