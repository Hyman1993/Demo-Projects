package com.example.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.domain.User;
@Repository
public interface UserRepository extends CrudRepository<User,Long> {}
