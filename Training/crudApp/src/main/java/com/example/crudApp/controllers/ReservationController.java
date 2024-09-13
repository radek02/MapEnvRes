package com.example.crudApp.controllers;

import com.example.crudApp.dtos.ApiResponseDTO;
import com.example.crudApp.dtos.ReservationDTO;
import com.example.crudApp.services.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {

    private ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDTO<?>> addReservation(@Valid @RequestBody ReservationDTO reservationDto)
    {
        return reservationService.addReservation(reservationDto);
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<?>> getAllReservations()
    {
        return reservationService.getAllReservations();
    }

    @GetMapping("/by-environment-id/{environmentID}")
    public ResponseEntity<ApiResponseDTO<?>> getReservationsByEnvironmentID(@PathVariable int environmentID) {
        return reservationService.getReservationsByEnvironmentID(environmentID);
    }

    @GetMapping("/by-id/{reservationID}")
    public ResponseEntity<ApiResponseDTO<?>> getReservationsByID(@PathVariable int reservationID) {
        return reservationService.getReservationsByID(reservationID);
    }

    @GetMapping("/by-usergroup-id/{userGroupID}")
    public ResponseEntity<ApiResponseDTO<?>> getReservationsByUserGroupID(@PathVariable int userGroupID) {
        return reservationService.getReservationsByUserGroupID(userGroupID);
    }

    @GetMapping("/by-cluster-id/{clusterID}")
    public ResponseEntity<ApiResponseDTO<?>> getReservationsByClusterID(@PathVariable int clusterID) {
        return reservationService.getReservationsByClusterID(clusterID);
    }
}
