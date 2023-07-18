package com.sda.karkonoszehiking.model.dto;

import com.sda.karkonoszehiking.model.entity.RouteEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HikeDto {


    private Long hikeId;
    private LocalDate date;
    private LocalTime duration;
    private List<RouteEntity> routes = new ArrayList<>();
    private String routeByWaypoints;


    @Override
    public String toString() {
        return "HikeDto{" +
                "hikeId=" + hikeId +
                ", date=" + date +
                ", duration=" + duration +
                ", routes=" + routes +
                '}';
    }
}
