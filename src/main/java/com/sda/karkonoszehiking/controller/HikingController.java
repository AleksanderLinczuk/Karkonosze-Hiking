package com.sda.karkonoszehiking.controller;


import com.sda.karkonoszehiking.model.dto.HikeDto;
import com.sda.karkonoszehiking.model.dto.WaypointDto;
import com.sda.karkonoszehiking.model.entity.AvailablePathsEntity;
import com.sda.karkonoszehiking.model.entity.HikeEntity;
import com.sda.karkonoszehiking.model.entity.RouteEntity;
import com.sda.karkonoszehiking.model.entity.WaypointEntity;
import com.sda.karkonoszehiking.repository.AvailablePathRepository;
import com.sda.karkonoszehiking.repository.HikeRepository;
import com.sda.karkonoszehiking.repository.RouteRepository;
import com.sda.karkonoszehiking.repository.WaypointRepository;
import com.sda.karkonoszehiking.service.HikeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/hiking")
public class HikingController {

    private final HikeRepository hikeRepository;
    private final HikeService hikeService;
    private final WaypointRepository waypointRepository;
    private final RouteRepository routeRepository;
    private final AvailablePathRepository availablePathRepository;

    public HikingController(HikeRepository hikeRepository, HikeService hikeService, WaypointRepository waypointRepository, RouteRepository routeRepository, AvailablePathRepository availablePathRepository) {
        this.hikeRepository = hikeRepository;
        this.hikeService = hikeService;
        this.waypointRepository = waypointRepository;
        this.routeRepository = routeRepository;
        this.availablePathRepository = availablePathRepository;
    }


    //main menu
    @GetMapping
    public String mainMenu(Model model) {
        model.addAttribute("name", "Karkonosze Hiking App :)");
        return "main";
    }

    @GetMapping("/all")
    public String all(Model model) {
        List<HikeDto> hikesFromDb = hikeService.getHikes();
        model.addAttribute("hikesFromDb", hikesFromDb);
        return "all";
    }

    @GetMapping("/visited")
    public String visited(Model model) {
        List<WaypointDto> waypointsFromDb = hikeService.getWaypoints();
        model.addAttribute("waypointsFromDb", waypointsFromDb);
        return "visited";
    }

    @GetMapping("/unvisited")
    public String unvisited(Model model) {
        List<WaypointDto> unvisitedWaypointsFromDb = hikeService.getUnvisitedWaypoints();
        model.addAttribute("unvisitedWaypointsFromDb", unvisitedWaypointsFromDb);
        return "unvisited";
    }

    @GetMapping("/delete")
    public String deleteHike() {
        return "delete";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Long id, Model model) {
        model.addAttribute("id", id);
        hikeService.deleteById(id);
        return "main";
    }

    @GetMapping("/add")
    public String add(Model model) {
        List<WaypointEntity> allWaypoints = waypointRepository.findAll();
        Map<String, List<String>> allWaypointsPaths = new HashMap<>();
        for (WaypointEntity waypoint : allWaypoints) {
            allWaypointsPaths.put(waypoint.getName(), waypoint.getAvailablePaths().stream().map(each -> each.getName()).collect(Collectors.toList()));
        }

        model.addAttribute("allWaypoints", allWaypoints);
        model.addAttribute("allWaypointsPaths", allWaypointsPaths);
        return "add";
    }


    @PostMapping("/submit")
    public String submit(@RequestParam Map<String, String> hikeParams, Model model) {

        LocalDate hikeDate = LocalDate.parse(hikeParams.get("date"));
        LocalTime hikeDuration = LocalTime.parse(hikeParams.get("duration"));
        List<RouteEntity> hikeRoutes = new ArrayList<>();

        List<String> waypoints = hikeParams.entrySet().stream().filter(entry -> entry.getKey().startsWith("waypoint")).map(entry -> entry.getValue()).collect(Collectors.toList());

        for (int i=0; i<waypoints.size(); i+=2){
            String waypoint1 = waypoints.get(i);
            String waypoint2 = waypoints.get(i+1);
            hikeRoutes.add(routeRepository.findRouteEntityByStartAndEnd(waypointRepository.findWaypointEntityByName(waypoint1).get(), availablePathRepository.findAvailablePathsEntityByName(waypoint2).get()).get());
        }

        HikeEntity savedHike = hikeRepository.save(new HikeEntity(hikeDate, hikeDuration, hikeRoutes));
        model.addAttribute("id", savedHike.getHikeId());
        model.addAttribute("date", savedHike.getDate());
        model.addAttribute("duration", savedHike.getDuration());
        model.addAttribute("routes", hikeService.getHikeWaypoints(savedHike.getHikeId()));

        return "result";
    }
}
