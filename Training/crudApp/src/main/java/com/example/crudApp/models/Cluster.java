package com.example.crudApp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "clusters")
public class Cluster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @NotBlank
    @Column(unique=true)
    private String name;

    // TODO - bidirectional mappings
    // @OneToMany(mappedBy = "cluster")
    // private Set<Reservation> reservations = new HashSet<>();
}
