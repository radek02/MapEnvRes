package com.example.crudApp.repositories;

import com.example.crudApp.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
    Optional<User> findByID(int id);

    Optional<User> findByUsername(String username);
}
