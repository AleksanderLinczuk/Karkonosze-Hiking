package com.sda.karkonoszehiking.service;

import com.sda.karkonoszehiking.model.dto.HikeDto;
import com.sda.karkonoszehiking.model.entity.AvailablePathsEntity;
import com.sda.karkonoszehiking.model.entity.HikeEntity;
import com.sda.karkonoszehiking.model.entity.RouteEntity;
import com.sda.karkonoszehiking.model.entity.WaypointEntity;
import com.sda.karkonoszehiking.repository.HikeRepository;
import com.sda.karkonoszehiking.repository.WaypointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class HikeServiceTest {

    @Mock
    private HikeRepository hikeRepository;

    @Mock
    private WaypointRepository waypointRepository;

    private HikeService hikeService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        hikeService = new HikeService(hikeRepository, waypointRepository);
    }

    @Test
    void getHikesShouldReturnListOfTwoElementsHikesDtos() {
        //Given
        WaypointEntity waypoint1 = new WaypointEntity(1L,"waypoint1",100,null,null);
        WaypointEntity waypoint2 = new WaypointEntity(2L,"waypoint2",200,null,null);
        AvailablePathsEntity availablePath1 = new AvailablePathsEntity(1L, "availablePath1", 100, null, null);
        AvailablePathsEntity availablePath2 = new AvailablePathsEntity(2L, "availablePath2", 200, null, null);
        RouteEntity route1 = new RouteEntity(1L,waypoint1,availablePath2,null, 5);
        RouteEntity route2 = new RouteEntity(2L,waypoint2,availablePath1,null, 5);

        waypoint1.setRoutes(List.of(route1));
        waypoint1.setAvailablePaths(List.of(availablePath2));

        waypoint2.setRoutes(List.of(route2));
        waypoint2.setAvailablePaths(List.of(availablePath1));

        availablePath1.setRoutes(List.of(route1));
        availablePath1.setWaypoints(List.of(waypoint2));

        availablePath2.setRoutes(List.of(route2));
        availablePath2.setWaypoints(List.of(waypoint1));

        HikeEntity hike1 = new HikeEntity(1L, LocalDate.now(), LocalTime.of(1,30),List.of(route1));
        HikeEntity hike2 = new HikeEntity(2L, LocalDate.of(2020,01,02), LocalTime.of(2,15),List.of(route2));

        route1.setHikes(List.of(hike1));
        route2.setHikes(List.of(hike2));

       List<HikeEntity> hikeList = List.of(hike1, hike2);

        when(hikeRepository.findAll()).thenReturn(hikeList);
        when(hikeRepository.findById(1L)).thenReturn(Optional.of(hike1));
        when(hikeRepository.findById(2L)).thenReturn(Optional.of(hike2));

        // When
        List<HikeDto> result = hikeService.getHikes();

        // Then
        assertEquals(2, result.size());

        HikeDto resultHike1 = result.get(0);
        assertEquals(1L, resultHike1.getHikeId());
        assertEquals(LocalDate.now(), resultHike1.getDate());
        assertEquals(LocalTime.of(1,30), resultHike1.getDuration());
        assertEquals(List.of(route1), resultHike1.getRoutes());

        HikeDto resultHike2 = result.get(1);
        assertEquals(2L, resultHike2.getHikeId());
        assertEquals(LocalDate.of(2020,01,02), resultHike2.getDate());
        assertEquals(LocalTime.of(2,15), resultHike2.getDuration());
        assertEquals(List.of(route2), resultHike2.getRoutes());
    }
}