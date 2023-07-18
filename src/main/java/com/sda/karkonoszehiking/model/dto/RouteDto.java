package com.sda.karkonoszehiking.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sda.karkonoszehiking.model.entity.AvailablePathsEntity;
import com.sda.karkonoszehiking.model.entity.HikeEntity;
import com.sda.karkonoszehiking.model.entity.WaypointEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data

@NoArgsConstructor
public class RouteDto {


    private Long routeId;
    private WaypointEntity start;
    private AvailablePathsEntity end;
    private List<HikeEntity> hikes = new ArrayList<>();
    private double length;

    public RouteDto(Long routeId, WaypointEntity start, AvailablePathsEntity end, List<HikeEntity> hikes, double length) {
        this.routeId = routeId;
        this.start = start;
        this.end = end;
        this.hikes = hikes;
        this.length = length;
    }
}
