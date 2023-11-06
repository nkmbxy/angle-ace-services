package com.project.angleace.repository;

import com.project.angleace.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer>, PagingAndSortingRepository<Manufacturer, Integer>, JpaSpecificationExecutor<Manufacturer> {
    Optional<Manufacturer> findByName(String name);
}
