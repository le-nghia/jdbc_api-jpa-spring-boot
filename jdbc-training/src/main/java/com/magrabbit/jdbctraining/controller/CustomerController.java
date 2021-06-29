package com.magrabbit.jdbctraining.controller;

import com.magrabbit.jdbctraining.entity.Customer;
import com.magrabbit.jdbctraining.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;

    public CustomerController(@Qualifier("customerJdbcService") CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    private List<Customer> getAll() {
        return customerService.getAll();
    }

    @GetMapping("/{id}")
    private Customer getById(@PathVariable("id") int id) {
        return customerService.getById(id);
    }

    @PostMapping
    private boolean save(@RequestBody Customer customer) {
        try {
            customerService.save(customer);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    @DeleteMapping("/{id}")
    private boolean delete(@PathVariable("id") int id) {
        try {
            customerService.delete(id);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    @PostMapping("/bulk")
    private boolean bulkSave(@RequestBody List<Customer> customers) {
        try {
            customerService.bulkSave(customers);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
        return true;

    }

    @DeleteMapping("/bulk")
    private boolean bulkDelete(@RequestBody List<Integer> ids) {
        try {
            customerService.bulkDelete(ids);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
        return true;

    }
}
