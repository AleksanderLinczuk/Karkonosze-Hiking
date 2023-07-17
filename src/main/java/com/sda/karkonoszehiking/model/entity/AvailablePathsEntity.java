package com.sda.karkonoszehiking.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "available_paths")
public class AvailablePathsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "available_path_id")
    private Long availablePathId;
    private String name;
    private double height;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "end")
    private List<RouteEntity> routes = new ArrayList<>();

    @JsonIgnore
    @ManyToMany (mappedBy = "availablePaths")
    private List<WaypointEntity> waypoints = new ArrayList<>();

    @Override
    public String toString() {
        return "AvailablePathsEntity{" +
                "id=" + availablePathId +
                ", name='" + name + '\'' +
                ", height=" + height +
                '}';
    }
}
