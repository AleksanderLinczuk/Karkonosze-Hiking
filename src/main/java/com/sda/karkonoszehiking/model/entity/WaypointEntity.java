package com.sda.karkonoszehiking.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "waypoints")
public class WaypointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "waypoint_id")
    private Long waypointId;
    private String name;
    private double height;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "start")
    private List<RouteEntity> routes = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "waypoints_available_paths",
            joinColumns = @JoinColumn(name = "waypoint_id"),
            inverseJoinColumns = @JoinColumn(name = "available_path_id"))
    private List<AvailablePathsEntity> availablePaths = new ArrayList<>();

    @Override
    public String toString() {
        return "WaypointEntity{" +
                "id=" + waypointId +
                ", name='" + name + '\'' +
                ", height=" + height +
                '}';
    }
}
