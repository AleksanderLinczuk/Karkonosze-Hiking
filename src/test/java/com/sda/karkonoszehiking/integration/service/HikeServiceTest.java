package com.sda.karkonoszehiking.integration.service;

import com.sda.karkonoszehiking.model.dto.HikeDto;
import com.sda.karkonoszehiking.model.entity.HikeEntity;
import com.sda.karkonoszehiking.model.entity.RouteEntity;
import com.sda.karkonoszehiking.repository.AvailablePathRepository;
import com.sda.karkonoszehiking.repository.HikeRepository;
import com.sda.karkonoszehiking.repository.RouteRepository;
import com.sda.karkonoszehiking.repository.WaypointRepository;
import com.sda.karkonoszehiking.service.AvailablePathService;
import com.sda.karkonoszehiking.service.HikeService;
import com.sda.karkonoszehiking.service.RouteService;
import com.sda.karkonoszehiking.service.WaypointService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.mock;

@SpringBootTest
class HikeServiceTest {


    @Autowired
    private HikeService hikeService;

    @Autowired
    private HikeRepository hikeRepository;

    @Autowired
    private WaypointService waypointService;

    @Autowired
    private WaypointRepository waypointRepository;


    @Autowired
    private AvailablePathService availablePathService;

    @Autowired
    AvailablePathRepository availablePathRepository;

    @Autowired
    private RouteService routeService;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    public void afterEachTest() {
        jdbcTemplate.execute("set foreign_key_checks = 0;");
        jdbcTemplate.execute("truncate table hikes;");
        jdbcTemplate.execute("truncate table hikes_routes;");
    }

    @Test
    public void testAddHike() {
        //Given
        LocalDate hikeDate = LocalDate.now();
        LocalTime hikeDuration = LocalTime.of(1, 20);
        HikeDto hikeDto = new HikeDto();
        hikeDto.setDate(hikeDate);
        hikeDto.setDuration(hikeDuration);
        RouteEntity route1 = routeService.findRouteEntityByStartAndEnd("Jagniątków", "Trzecia Droga").get();
        RouteEntity route2 = routeService.findRouteEntityByStartAndEnd("Trzecia Droga", "Rozdroże Pod Śmielcem").get();
        RouteEntity route3 = routeService.findRouteEntityByStartAndEnd("Rozdroże Pod Śmielcem", "Czarna Przełęcz").get();
        List<RouteEntity> routes = new ArrayList<>();
        routes.add(route1);
        routes.add(route2);
        routes.add(route3);
        hikeDto.setRoutes(routes);


        //When
        hikeService.save(hikeDto);

        //Then
        String sql = "select hike_id, date, duration from hikes where hike_id=1;";
        RowMapper<HikeEntity> rowMapper = (rs, rowNum) -> new HikeEntity(rs.getLong("hike_id"), rs.getDate("date").toLocalDate(), rs.getTime("duration").toLocalTime());
        HikeEntity hikeEntity = jdbcTemplate.queryForObject(sql, rowMapper);

        assertEquals(1L,hikeEntity.getHikeId());
        assertEquals(hikeDate,hikeEntity.getDate());
        assertEquals(hikeDuration,hikeEntity.getDuration());
        assertEquals(1, hikeService.countHikes());

        HikeEntity placeHolder = new HikeEntity();
        placeHolder.setRoutes(routes);
        HikeEntity hikeEntity2 = hikeService.findById(hikeEntity.getHikeId()).get();
        assertEquals(1L,hikeEntity2.getHikeId());
        assertEquals(hikeDate,hikeEntity2.getDate());
        assertEquals(hikeDuration,hikeEntity2.getDuration());
        assertEquals(routes.toString(),hikeEntity2.getRoutes().toString());
        //assertArray(routes, hikeEntity2.getRoutes().stream().collect(Collectors.toList()));
    }



}
