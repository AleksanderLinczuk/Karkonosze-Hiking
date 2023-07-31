package com.sda.karkonoszehiking.service;

import com.sda.karkonoszehiking.model.entity.AvailablePathsEntity;
import com.sda.karkonoszehiking.model.entity.RouteEntity;
import com.sda.karkonoszehiking.model.entity.WaypointEntity;
import com.sda.karkonoszehiking.repository.AvailablePathRepository;
import com.sda.karkonoszehiking.repository.RouteRepository;
import com.sda.karkonoszehiking.repository.WaypointRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RouteService {

    private final RouteRepository routeRepository;
    private final WaypointRepository waypointRepository;
    private final AvailablePathRepository availablePathRepository;


    public RouteService(RouteRepository routeRepository, WaypointRepository waypointRepository, AvailablePathRepository availablePathRepository) {
        this.routeRepository = routeRepository;
        this.waypointRepository = waypointRepository;
        this.availablePathRepository = availablePathRepository;
    }

    public Optional<RouteEntity> findRouteEntityByStartAndEnd(WaypointEntity waypointEntity, AvailablePathsEntity availablePathsEntity) {
        return routeRepository.findRouteEntityByStartAndEnd(waypointEntity,availablePathsEntity);
    }
    public Optional<RouteEntity> findRouteEntityByStartAndEnd(String startName, String endName) {
        return routeRepository.findRouteEntityByStartAndEnd(waypointRepository.findWaypointEntityByName(startName).get(),availablePathRepository.findAvailablePathsEntityByName(endName).get());
    }
}
