package com.example.crudApp.repositories;

import com.example.crudApp.models.User;
import com.example.crudApp.models.UserGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserGroupRepository extends CrudRepository<UserGroup, Integer> {
    List<UserGroup> findAll();
    Optional<UserGroup> findByID(int id);
}
