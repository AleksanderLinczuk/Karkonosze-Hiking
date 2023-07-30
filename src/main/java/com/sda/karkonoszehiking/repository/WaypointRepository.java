package com.sda.karkonoszehiking.repository;

import com.sda.karkonoszehiking.model.entity.AvailablePathsEntity;
import com.sda.karkonoszehiking.model.entity.RouteEntity;
import com.sda.karkonoszehiking.model.entity.WaypointEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WaypointRepository extends JpaRepository<WaypointEntity, Long> {

    Optional<WaypointEntity> findWaypointEntityByName(String name);
}
