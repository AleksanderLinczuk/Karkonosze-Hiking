package com.sda.karkonoszehiking.controller;


import com.sda.karkonoszehiking.model.dto.HikeDto;
import com.sda.karkonoszehiking.model.dto.WaypointDto;
import com.sda.karkonoszehiking.model.entity.HikeEntity;
import com.sda.karkonoszehiking.model.entity.RouteEntity;
import com.sda.karkonoszehiking.model.entity.WaypointEntity;
import com.sda.karkonoszehiking.service.AvailablePathService;
import com.sda.karkonoszehiking.service.HikeService;
import com.sda.karkonoszehiking.service.RouteService;
import com.sda.karkonoszehiking.service.WaypointService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/hiking")
public class HikingController {

    private final HikeService hikeService;
    private final RouteService routeService;
    private final WaypointService waypointService;
    private final AvailablePathService availablePathService;


    public HikingController(HikeService hikeService, RouteService routeService, WaypointService waypointService, AvailablePathService availablePathService) {
        this.hikeService = hikeService;
        this.routeService = routeService;
        this.waypointService = waypointService;
        this.availablePathService = availablePathService;
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
        List<WaypointDto> waypointsFromDb = hikeService.getVisitedWaypoints();
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
        if (hikeService.findById(id).isEmpty()){
            model.addAttribute("id", id);
            return "not_found";
        }
        hikeService.deleteById(id);
        return "main";
    }

    @GetMapping("/add")
    public String add(Model model) {
        List<WaypointEntity> allWaypoints = waypointService.findAll();
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
            hikeRoutes.add(routeService.findRouteEntityByStartAndEnd(waypoint1, waypoint2).get());
        }

        HikeEntity savedHike = hikeService.save(new HikeEntity(hikeDate, hikeDuration, hikeRoutes));
        model.addAttribute("id", savedHike.getHikeId());
        model.addAttribute("date", savedHike.getDate());
        model.addAttribute("duration", savedHike.getDuration());
        model.addAttribute("routes", hikeService.getHikeWaypoints(savedHike.getHikeId()));

        return "result";
    }
}
