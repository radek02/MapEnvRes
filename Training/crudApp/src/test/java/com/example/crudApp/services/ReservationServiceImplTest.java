package com.example.crudApp.services;

import com.example.crudApp.dtos.ApiResponseDTO;
import com.example.crudApp.dtos.ApiResponseStatusDTO;
import com.example.crudApp.dtos.ReservationDTO;
import com.example.crudApp.models.*;
import com.example.crudApp.repositories.ClusterRepository;
import com.example.crudApp.repositories.EnvironmentRepository;
import com.example.crudApp.repositories.ReservationRepository;
import com.example.crudApp.repositories.UserGroupRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class ReservationServiceImplTest {

    @Mock
    private EnvironmentRepository environmentRepository;
    @Mock
    private ClusterRepository clusterRepository;
    @Mock
    private UserGroupRepository userGroupRepository;
    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private Environment environment = new Environment();
    private Cluster cluster = new Cluster();
    private UserGroup userGroup = new UserGroup();

    // Custom fields:
    private final LocalDateTime curTime = LocalDateTime.now();
    private final ReservationDTO inputReservationDTO =
            new ReservationDTO(userGroup.getID(), cluster.getID(), environment.getID(), curTime, curTime.plusHours(2));
    private final Reservation reservation =
            new Reservation(environment, cluster, userGroup, inputReservationDTO.getStartTime(), inputReservationDTO.getEndTime());
    private final ReservationDTO outputReservationDTO = new ReservationDTO(reservation);
    private final List<Reservation> reservationsList = Collections.singletonList(reservation);
    private final List<ReservationDTO> reservationsDTOList = Collections.singletonList(outputReservationDTO);

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = openMocks(this);

        when(environmentRepository.findByID(inputReservationDTO.getEnvironmentID())).thenReturn(Optional.of(environment));
        when(clusterRepository.findByID(inputReservationDTO.getClusterID())).thenReturn(Optional.of(cluster));
        when(userGroupRepository.findByID(inputReservationDTO.getUserGroupID())).thenReturn(Optional.of(userGroup));
        when(reservationRepository.findConflictingReservations(any())).thenReturn(reservationsList);
        when(reservationRepository.findAll()).thenReturn(reservationsList);
        when(reservationRepository.findByEnvironment(environment)).thenReturn(reservationsList);
        when(reservationRepository.findByID(reservation.getID())).thenReturn(Optional.of(reservation));
        when(userGroupRepository.findByID(userGroup.getID())).thenReturn(Optional.of(userGroup));
        when(reservationRepository.findByUserGroup(userGroup)).thenReturn(reservationsList);
    }

    @AfterEach
    void cleanUp() throws Exception {
        closeable.close();
    }

    @Test
    void addReservationSuccessfully() {
        when(reservationRepository.findConflictingReservations(any())).thenReturn(Collections.emptyList());

        ResponseEntity<ApiResponseDTO<?>> response = reservationService.addReservation(inputReservationDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ApiResponseStatusDTO.SUCCESS.name(), Objects.requireNonNull(response.getBody()).getStatus());
        verify(reservationRepository).save(any(Reservation.class));
    }

    @Test
    void addReservationConflict() {
        when(reservationRepository.findConflictingReservations(any())).thenReturn(reservationsList);

        ApiResponseDTO<?> expectedResponse = new ApiResponseDTO<>(ApiResponseStatusDTO.FAIL.name(),
                "Resource is already reserved for the given time.");
        ResponseEntity<ApiResponseDTO<?>> response = reservationService.addReservation(inputReservationDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(expectedResponse.getStatus(), Objects.requireNonNull(response.getBody()).getStatus());
        verify(reservationRepository, never()).save(reservation);
    }

    @Test
    void getAllReservationsSuccessfully() {
        when(reservationRepository.findAll()).thenReturn(reservationsList);

        ApiResponseDTO<?> expectedResponse = new ApiResponseDTO<>(ApiResponseStatusDTO.SUCCESS.name(), reservationsDTOList);
        ResponseEntity<ApiResponseDTO<?>> response = reservationService.getAllReservations();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void getReservationsByEnvironmentIDSuccessfully() {
        ApiResponseDTO<?> expectedResponse = new ApiResponseDTO<>(ApiResponseStatusDTO.SUCCESS.name(), reservationsDTOList);

        ResponseEntity<ApiResponseDTO<?>> response = reservationService.getReservationsByEnvironmentID(environment.getID());

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void getReservationsByEnvironmentIDNotFound() {
        int environmentID = environment.getID();

        when(environmentRepository.findByID(environmentID)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponseDTO<?>> response = reservationService.getReservationsByEnvironmentID(environment.getID());

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getReservationsByIDSuccessfully() {
        int reservationID = reservation.getID();

        ApiResponseDTO<ReservationDTO> expectedResponse = new ApiResponseDTO<>(ApiResponseStatusDTO.SUCCESS.name(), outputReservationDTO);

        ResponseEntity<ApiResponseDTO<?>> response = reservationService.getReservationsByID(reservationID);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void getReservationsByIDNotFound() {
        int reservationID = reservation.getID();

        when(reservationRepository.findByID(reservationID)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponseDTO<?>> response = reservationService.getReservationsByID(reservationID);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getReservationsByUserGroupIDSuccessfully() {
        int userGroupID = userGroup.getID();

        ApiResponseDTO<Iterable<ReservationDTO>> expectedResponse = new ApiResponseDTO<>(ApiResponseStatusDTO.SUCCESS.name(), reservationsDTOList);

        ResponseEntity<ApiResponseDTO<?>> response = reservationService.getReservationsByUserGroupID(userGroupID);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void getReservationsByUserGroupIDNotFound() {
        int userGroupID = userGroup.getID();

        when(userGroupRepository.findByID(userGroupID)).thenReturn(Optional.empty());

        ResponseEntity<ApiResponseDTO<?>> response = reservationService.getReservationsByUserGroupID(userGroupID);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
