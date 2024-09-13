package com.example.crudApp.dtos;

import com.example.crudApp.models.Reservation;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private Integer ID = null;

    @NotNull(message = "User group ID is mandatory")
    private Integer userGroupID;

    @NotNull(message = "Cluster ID is mandatory")
    private Integer clusterID;

    @NotNull(message = "Environment ID is mandatory")
    private Integer environmentID;

    @NotNull(message = "Start time is mandatory")
    private LocalDateTime startTime;

    @NotNull(message = "End time is mandatory")
    private LocalDateTime endTime;

    public ReservationDTO(Reservation reservation)
    {
        this.ID = reservation.getID();
        this.userGroupID = reservation.getUserGroup().getID();
        this.clusterID = reservation.getCluster().getID();
        this.environmentID = reservation.getEnvironment().getID();
        this.startTime = reservation.getStartTime();
        this.endTime = reservation.getEndTime();
    }

    public ReservationDTO(Integer userGroupID, Integer clusterID, Integer environmentID, LocalDateTime startTime, LocalDateTime endTime)
    {
        if(!startTime.isBefore(endTime))
            throw new IllegalArgumentException("Start time must be before end time!");

        this.userGroupID = userGroupID;
        this.clusterID = clusterID;
        this.environmentID = environmentID;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
