package com.example.crudApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @ManyToOne
    @JoinColumn(name = "environment_id")
    private Environment environment;

    @ManyToOne
    @JoinColumn(name = "cluster_id")
    private Cluster cluster;

    @ManyToOne
    @JoinColumn(name = "usergroup_id")
    private UserGroup userGroup;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    @AssertTrue(message = "End date must be after start date")
    private boolean isValid() {
        return startTime.isBefore(endTime);
    }

    public Reservation(Environment environment, Cluster cluster, UserGroup userGroup, LocalDateTime startTime, LocalDateTime endTime) {
        this.environment = environment;
        this.cluster = cluster;
        this.userGroup = userGroup;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
