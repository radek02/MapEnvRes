package com.example.crudApp.repositories;

import com.example.crudApp.models.Cluster;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClusterRepository extends CrudRepository<Cluster, Integer> {
    Optional<Cluster> findByID(int id);
}
