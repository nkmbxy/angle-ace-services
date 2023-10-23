package com.project.angleace.repository;

import com.project.angleace.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Integer>, PagingAndSortingRepository<CustomerOrder, Integer>, JpaSpecificationExecutor<CustomerOrder> {
}
