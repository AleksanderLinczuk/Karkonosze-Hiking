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
@Table(name = "routes")
public class RouteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "route_id")
    private Long routeId;

    @ManyToOne
    private WaypointEntity  start;

    @ManyToOne
    private AvailablePathsEntity  end;

    @JsonIgnore
    @ManyToMany (mappedBy = "routes")
    private List<HikeEntity> hikes = new ArrayList<>();

    private double length;

    @Override
    public String toString() {
        return "RouteEntity{" +
                "routeId=" + routeId +
                ", start=" + start.getName() +
                ", end=" + end.getName() +
                ", length=" + length +
                '}';
    }
}
