package com.example.crudApp.controllers;

import com.example.crudApp.dtos.ApiResponseDTO;
import com.example.crudApp.dtos.ReservationDTO;
import com.example.crudApp.services.ReservationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class ReservationControllerTest {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = openMocks(this);
    }

    @AfterEach
    void cleanUp() throws Exception {
        closeable.close();
    }

    @Test
    void addReservationTest() {
        ApiResponseDTO<?> expectedResponse = new ApiResponseDTO<>("...", "...");

        when(reservationService.addReservation(any(ReservationDTO.class))).thenReturn(ResponseEntity.ok(expectedResponse));

        ResponseEntity<ApiResponseDTO<?>> response = reservationController.addReservation(
                new ReservationDTO(1, 1, 1, LocalDateTime.now(), LocalDateTime.now().plusHours(2)));

        assertNotNull(response);
        assertEquals(expectedResponse, response.getBody());
        verify(reservationService).addReservation(any(ReservationDTO.class));
    }

    @Test
    void getAllReservationsTest() {
        ApiResponseDTO<Iterable<ReservationDTO>> expectedResponse = new ApiResponseDTO<>("...", new ArrayList<>());

        when(reservationService.getAllReservations()).thenReturn(ResponseEntity.ok(expectedResponse));

        ResponseEntity<ApiResponseDTO<?>> response = reservationController.getAllReservations();

        assertNotNull(response);
        assertEquals(expectedResponse, response.getBody());
        verify(reservationService).getAllReservations();
    }
}