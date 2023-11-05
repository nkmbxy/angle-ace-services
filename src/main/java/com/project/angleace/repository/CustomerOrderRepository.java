package com.project.angleace.repository;

import com.project.angleace.entity.CustomerOrder;
import com.project.angleace.model.response.SummaryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Integer>, PagingAndSortingRepository<CustomerOrder, Integer>, JpaSpecificationExecutor<CustomerOrder> {

    @Query("SELECT new com.project.angleace.model.response.SummaryModel(co.createdAt, SUM(co.profit)) " +
            "FROM CustomerOrder co " +
            "WHERE (:startDate IS NULL OR co.createdAt >= :startDate) " +
            "AND (:endDate IS NULL OR co.createdAt <= :endDate) " +
            "GROUP BY co.createdAt")
    List<SummaryModel> findProfitSummary(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
