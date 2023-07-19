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

    public WaypointDto(Long waypointId, String name, double height) {
        this.waypointId = waypointId;
        this.name = name;
        this.height = height;
    }
}
