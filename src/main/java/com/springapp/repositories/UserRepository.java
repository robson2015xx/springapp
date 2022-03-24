package com.springapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springapp.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
