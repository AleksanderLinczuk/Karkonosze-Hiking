package com.sda.karkonoszehiking.service;

import com.sda.karkonoszehiking.model.entity.AvailablePathsEntity;
import com.sda.karkonoszehiking.model.entity.WaypointEntity;
import com.sda.karkonoszehiking.repository.WaypointRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WaypointService {

    private final WaypointRepository waypointRepository;

    public WaypointService(WaypointRepository waypointRepository, HikeService hikeService) {
        this.waypointRepository = waypointRepository;
    }


    public List<WaypointEntity> findAll() {
        return waypointRepository.findAll();
    }


    public Optional<WaypointEntity> findWaypointEntityByName(String name) {
        return waypointRepository.findWaypointEntityByName(name);
    }



}
