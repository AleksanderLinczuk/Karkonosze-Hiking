package com.sda.karkonoszehiking.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sda.karkonoszehiking.model.entity.RouteEntity;
import com.sda.karkonoszehiking.model.entity.WaypointEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class AvailablePathsDto {


    private Long availablePathId;
    private String name;
    private double height;
    private List<RouteEntity> routes = new ArrayList<>();
    private List<WaypointEntity> waypoints = new ArrayList<>();

    public AvailablePathsDto(Long availablePathId, String name, double height, List<RouteEntity> routes, List<WaypointEntity> waypoints) {
        this.availablePathId = availablePathId;
        this.name = name;
        this.height = height;
        this.routes = routes;
        this.waypoints = waypoints;
    }
}
