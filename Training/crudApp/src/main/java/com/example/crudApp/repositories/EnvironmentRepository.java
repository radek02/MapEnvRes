package com.example.crudApp.repositories;

import com.example.crudApp.models.Environment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnvironmentRepository extends CrudRepository<Environment, Integer>{
    Optional<Environment> findByID(int id);
}
