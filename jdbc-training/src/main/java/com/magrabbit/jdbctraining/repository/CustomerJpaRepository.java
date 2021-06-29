package com.magrabbit.jdbctraining.repository;

import com.magrabbit.jdbctraining.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerJpaRepository extends JpaRepository<Customer, Integer> {
    void deleteByIdIn(List<Integer> ids);
}
