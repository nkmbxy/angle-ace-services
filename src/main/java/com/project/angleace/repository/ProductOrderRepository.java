package com.project.angleace.repository;

import com.project.angleace.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Integer>, PagingAndSortingRepository<ProductOrder, Integer>, JpaSpecificationExecutor<ProductOrder> {
}