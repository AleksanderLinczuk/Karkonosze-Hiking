package com.sda.karkonoszehiking.model.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "hikes")
public class HikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "hike_id")
    private Long hikeId;
    private LocalDate date;
    private LocalTime duration;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable (name = "hikes_routes",
            joinColumns = @JoinColumn (name = "hike_id"),
            inverseJoinColumns = @JoinColumn (name = "route_id"))
    private List<RouteEntity> routes = new ArrayList<>();

    public HikeEntity(LocalDate date, LocalTime duration, List<RouteEntity> routes) {
        this.date = date;
        this.duration = duration;
        this.routes = routes;
    }

    public HikeEntity(Long hikeId, LocalDate date, LocalTime duration) {
        this.hikeId = hikeId;
        this.date = date;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "HikeEntity{" +
                "hikeId=" + hikeId +
                ", date=" + date +
                ", duration=" + duration +
                ", routes=" + routes +
                '}';
    }
}