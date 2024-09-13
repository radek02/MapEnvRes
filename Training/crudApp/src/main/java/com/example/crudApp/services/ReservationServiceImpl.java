package com.example.crudApp.services;

import com.example.crudApp.dtos.ApiResponseDTO;
import com.example.crudApp.dtos.ApiResponseStatusDTO;
import com.example.crudApp.dtos.ReservationDTO;
import com.example.crudApp.models.Cluster;
import com.example.crudApp.models.Environment;
import com.example.crudApp.models.Reservation;
import com.example.crudApp.models.UserGroup;
import com.example.crudApp.repositories.ClusterRepository;
import com.example.crudApp.repositories.EnvironmentRepository;
import com.example.crudApp.repositories.ReservationRepository;
import com.example.crudApp.repositories.UserGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    private final EnvironmentRepository environmentRepository;
    private final ClusterRepository clusterRepository;
    private final UserGroupRepository userGroupRepository;
    private final ReservationRepository reservationRepository;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseEntity<ApiResponseDTO<?>> addReservation(ReservationDTO reservationDetails)
    {
        try {
            Optional<Environment> environment = environmentRepository.findByID(reservationDetails.getEnvironmentID());
            Optional<Cluster> cluster = clusterRepository.findByID(reservationDetails.getClusterID());
            Optional<UserGroup> userGroup = userGroupRepository.findByID(reservationDetails.getUserGroupID());
            LocalDateTime startTime = reservationDetails.getStartTime();
            LocalDateTime endTime = reservationDetails.getEndTime();

            if(!environment.isPresent() || !cluster.isPresent() || !userGroup.isPresent())
            {
                String message = "";

                if(!environment.isPresent())
                    message = "Environment not found";
                else if(!cluster.isPresent())
                    message = "Cluster not found";
                else if(!userGroup.isPresent())
                    message = "User Group not found";

                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponseDTO<>(ApiResponseStatusDTO.FAIL.name(), message));
            }

            Reservation reservation =
                    new Reservation(environment.get(), cluster.get(), userGroup.get(), startTime, endTime);

            // Checks if the resource is already reserved for the given time.
            if(!reservationRepository.findConflictingReservations(reservation).isEmpty())
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(new ApiResponseDTO<>(ApiResponseStatusDTO.FAIL.name(),
                                "Resource is already reserved for the given time."));

            reservationRepository.save(reservation);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ApiResponseDTO<>(ApiResponseStatusDTO.SUCCESS.name(),
                            "New reservation added successfully."));
        }
        catch(Exception e) {
            log.error("Exception during adding reservation: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseEntity<ApiResponseDTO<?>> getAllReservations() {
        try {
            List<ReservationDTO> reservations = reservationRepository.findAll()
                    .stream().map(ReservationDTO::new).toList();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponseDTO<>(ApiResponseStatusDTO.SUCCESS.name(), reservations)
                    );
        }
        catch(Exception e) {
            log.error("Exception during fetching all reservations: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseEntity<ApiResponseDTO<?>> getReservationsByEnvironmentID(int environmentID) {
        try {
            Optional<Environment> environment = environmentRepository.findByID(environmentID);

            if(!environment.isPresent())
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponseDTO<>(ApiResponseStatusDTO.FAIL.name(), "Environment not found"));

            List<ReservationDTO> reservations = reservationRepository.findByEnvironment(environment.get())
                    .stream().map(ReservationDTO::new).toList();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponseDTO<>(ApiResponseStatusDTO.SUCCESS.name(), reservations)
                    );
        }
        catch(Exception e) {
            log.error("Exception during fetching reservations by environmentID: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseEntity<ApiResponseDTO<?>> getReservationsByID(int reservationID) {
        try {
            Optional<Reservation> reservation = reservationRepository.findByID(reservationID);

            if(!reservation.isPresent())
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponseDTO<>(ApiResponseStatusDTO.FAIL.name(), "Reservation not found"));

            ReservationDTO reservationDTO = new ReservationDTO(reservation.get());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponseDTO<>(ApiResponseStatusDTO.SUCCESS.name(), reservationDTO)
                    );
        }
        catch(Exception e) {
            log.error("Exception during fetching reservation by ID: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseEntity<ApiResponseDTO<?>> getReservationsByUserGroupID(int userGroupID) {
        try {
            Optional<UserGroup> userGroup = userGroupRepository.findByID(userGroupID);

            if(!userGroup.isPresent())
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponseDTO<>(ApiResponseStatusDTO.FAIL.name(), "User group not found"));

            List<ReservationDTO> reservations = reservationRepository.findByUserGroup(userGroup.get())
                    .stream().map(ReservationDTO::new).toList();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponseDTO<>(ApiResponseStatusDTO.SUCCESS.name(), reservations)
                    );
        }
        catch(Exception e) {
            log.error("Exception during fetching reservations by user group: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseEntity<ApiResponseDTO<?>> getReservationsByClusterID(int clusterID) {
        try {
            Optional<Cluster> cluster = clusterRepository.findByID(clusterID);

            if(!cluster.isPresent())
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponseDTO<>(ApiResponseStatusDTO.FAIL.name(), "Cluster not found"));

            List<ReservationDTO> reservations = reservationRepository.findByCluster(cluster.get())
                    .stream().map(ReservationDTO::new).toList();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponseDTO<>(ApiResponseStatusDTO.SUCCESS.name(), reservations)
                    );
        }
        catch(Exception e) {
            log.error("Exception during fetching reservations by clusterID: {}", e.getMessage());
            throw e;
        }
    }
}
