package com.project.angleace.repository;

import com.project.angleace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository  extends JpaRepository<User, Integer>, PagingAndSortingRepository<User, Integer>, JpaSpecificationExecutor<User> {
}
