package com.magrabbit.jdbctraining.service.impl;

import com.magrabbit.jdbctraining.dao.CustomerJdbcDao;
import com.magrabbit.jdbctraining.entity.Customer;
import com.magrabbit.jdbctraining.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerJdbcService implements CustomerService {

    private static final String REPOSITORY_BEAN = "customerJdbcTemplateDao";

    private static final Logger LOG = LoggerFactory.getLogger(CustomerJdbcService.class);

    private final CustomerJdbcDao customerJdbcDao;

    public CustomerJdbcService(@Qualifier(REPOSITORY_BEAN) CustomerJdbcDao customerJdbcDao) {
        this.customerJdbcDao = customerJdbcDao;
    }

    @Override
    public List<Customer> getAll() {
        LOG.info("********* {}::findAll", REPOSITORY_BEAN);
        return customerJdbcDao.findAll();
    }

    @Override
    public Customer getById(int id) {
        LOG.info("********* {}::findById", REPOSITORY_BEAN);
        return customerJdbcDao.findById(id);
    }

    @Override
    @Transactional
    public void save(Customer customer) {
        if (customer.getId() == 0) {
            LOG.info("********* {}::insert", REPOSITORY_BEAN);
            customerJdbcDao.insert(customer);
        } else {
            LOG.info("********* {}::update", REPOSITORY_BEAN);
            customerJdbcDao.update(customer);
        }
    }

    @Override
    @Transactional
    public void delete(int id) {
        LOG.info("********* {}::delete", REPOSITORY_BEAN);
        customerJdbcDao.delete(id);
    }

    @Override
    @Transactional
    public void bulkSave(List<Customer> customers) {
        List<Customer> customersToInsert = customers.stream().filter(p -> p.getId() == 0).collect(Collectors.toList());
        if (!customersToInsert.isEmpty()) {
            LOG.info("********* {}::batchInsert", REPOSITORY_BEAN);
            customerJdbcDao.batchInsert(customersToInsert);
        }
        List<Customer> customersToUpdate = customers.stream().filter(p -> p.getId() != 0).collect(Collectors.toList());
        if (!customersToUpdate.isEmpty()) {
            LOG.info("********* {}::batchUpdate", REPOSITORY_BEAN);
            customerJdbcDao.batchUpdate(customersToUpdate);
        }
    }

    @Override
    @Transactional
    public void bulkDelete(List<Integer> ids) {
        LOG.info("********* {}::batchDelete", REPOSITORY_BEAN);
        customerJdbcDao.batchDelete(ids);
    }
}
