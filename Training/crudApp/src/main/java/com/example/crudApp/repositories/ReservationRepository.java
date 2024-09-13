package com.example.crudApp.repositories;

import com.example.crudApp.models.Cluster;
import com.example.crudApp.models.Environment;
import com.example.crudApp.models.Reservation;
import com.example.crudApp.models.UserGroup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Integer> {

    List<Reservation> findAll();

    Optional<Reservation> findByID(int id);

    List<Reservation> findByEnvironment(Environment environment);

    List<Reservation> findByCluster(Cluster cluster);

    List<Reservation> findByUserGroup(UserGroup userGroup);

    @Query("SELECT r FROM Reservation r WHERE r.environment = :#{#newReservation.environment} AND r.cluster = :#{#newReservation.cluster} AND " +
            "((:#{#newReservation.startTime} >= r.startTime AND :#{#newReservation.startTime} < r.endTime) " +
            "OR (:#{#newReservation.endTime} > r.startTime AND :#{#newReservation.endTime} <= r.endTime) " +
            "OR (:#{#newReservation.startTime} <= r.startTime AND :#{#newReservation.endTime} >= r.endTime))")
    List<Reservation> findConflictingReservations(Reservation newReservation);

}
