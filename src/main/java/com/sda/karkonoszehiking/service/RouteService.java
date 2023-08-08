package com.sda.karkonoszehiking.service;

import com.sda.karkonoszehiking.model.entity.AvailablePathsEntity;
import com.sda.karkonoszehiking.model.entity.RouteEntity;
import com.sda.karkonoszehiking.model.entity.WaypointEntity;
import com.sda.karkonoszehiking.repository.AvailablePathRepository;
import com.sda.karkonoszehiking.repository.RouteRepository;
import com.sda.karkonoszehiking.repository.WaypointRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RouteService {

    private final RouteRepository routeRepository;
    private final WaypointService waypointService;
    private final AvailablePathService availablePathService;


    public RouteService(RouteRepository routeRepository, WaypointRepository waypointRepository, AvailablePathRepository availablePathRepository, WaypointService waypointService, AvailablePathService availablePathService) {
        this.routeRepository = routeRepository;
        this.waypointService = waypointService;
        this.availablePathService = availablePathService;
    }

    public Optional<RouteEntity> findRouteEntityByStartAndEnd(WaypointEntity waypointEntity, AvailablePathsEntity availablePathsEntity) {
        return routeRepository.findRouteEntityByStartAndEnd(waypointEntity,availablePathsEntity);
    }
    public Optional<RouteEntity> findRouteEntityByStartAndEnd(String startName, String endName) {
        return findRouteEntityByStartAndEnd(waypointService.findWaypointEntityByName(startName).get(),availablePathService.findAvailablePathsEntityByName(endName).get());
    }

}
