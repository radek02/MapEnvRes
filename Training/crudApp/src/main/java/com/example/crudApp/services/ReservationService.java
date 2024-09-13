package com.example.crudApp.services;

import com.example.crudApp.dtos.ApiResponseDTO;
import com.example.crudApp.dtos.ReservationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ReservationService {

    ResponseEntity<ApiResponseDTO<?>> addReservation(ReservationDTO newUserDetails);

    ResponseEntity<ApiResponseDTO<?>> getAllReservations();

    ResponseEntity<ApiResponseDTO<?>> getReservationsByEnvironmentID(int environmentID);

    ResponseEntity<ApiResponseDTO<?>> getReservationsByID(int reservationID);

    ResponseEntity<ApiResponseDTO<?>> getReservationsByUserGroupID(int userGroupID);

    ResponseEntity<ApiResponseDTO<?>> getReservationsByClusterID(int clusterID);
}

