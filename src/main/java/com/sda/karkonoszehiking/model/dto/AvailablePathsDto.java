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

    public AvailablePathsDto(Long availablePathId, String name, double height) {
        this.availablePathId = availablePathId;
        this.name = name;
        this.height = height;
    }
}
