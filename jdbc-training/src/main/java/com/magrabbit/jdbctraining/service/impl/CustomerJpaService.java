package com.magrabbit.jdbctraining.service.impl;

import com.magrabbit.jdbctraining.repository.CustomerJpaRepository;
import com.magrabbit.jdbctraining.entity.Customer;
import com.magrabbit.jdbctraining.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerJpaService implements CustomerService {

    private static final String REPOSITORY_BEAN = "customerJpaRepository";

    private static final Logger LOG = LoggerFactory.getLogger(CustomerJpaService.class);

    private final CustomerJpaRepository customerJpaRepository;

    public CustomerJpaService(CustomerJpaRepository customerJpaRepository) {
        this.customerJpaRepository = customerJpaRepository;
    }

    @Override
    public List<Customer> getAll() {
        LOG.info("********* {}::findAll", REPOSITORY_BEAN);
        return customerJpaRepository.findAll();
    }

    @Override
    public Customer getById(int id) {
        LOG.info("********* {}::findById", REPOSITORY_BEAN);
        return customerJpaRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void save(Customer customer) {
        LOG.info("********* {}::save", REPOSITORY_BEAN);
        customerJpaRepository.save(customer);
    }

    @Override
    @Transactional
    public void delete(int id) {
        LOG.info("********* {}::deleteById", REPOSITORY_BEAN);
        customerJpaRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void bulkSave(List<Customer> customers) {
        LOG.info("********* {}::saveAll", REPOSITORY_BEAN);
        customerJpaRepository.saveAll(customers);
    }

    @Override
    @Transactional
    public void bulkDelete(List<Integer> ids) {
        LOG.info("********* {}::deleteByIdIn", REPOSITORY_BEAN);
        customerJpaRepository.deleteByIdIn(ids);
    }
}
