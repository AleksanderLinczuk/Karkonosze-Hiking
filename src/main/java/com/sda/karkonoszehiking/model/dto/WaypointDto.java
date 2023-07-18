package com.sda.karkonoszehiking.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sda.karkonoszehiking.model.entity.AvailablePathsEntity;
import com.sda.karkonoszehiking.model.entity.RouteEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class WaypointDto {


    private Long waypointId;
    private String name;
    private double height;
    private List<RouteEntity> routes = new ArrayList<>();
    private List<AvailablePathsEntity> availablePaths = new ArrayList<>();

    public WaypointDto(Long waypointId, String name, double height, List<RouteEntity> routes, List<AvailablePathsEntity> availablePaths) {
        this.waypointId = waypointId;
        this.name = name;
        this.height = height;
        this.routes = routes;
        this.availablePaths = availablePaths;
    }
}
