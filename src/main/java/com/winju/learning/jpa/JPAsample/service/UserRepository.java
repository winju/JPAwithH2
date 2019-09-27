package com.winju.learning.jpa.JPAsample.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.winju.learning.jpa.JPAsample.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
