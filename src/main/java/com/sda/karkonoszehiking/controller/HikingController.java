package com.sda.karkonoszehiking.controller;


import com.sda.karkonoszehiking.model.dto.HikeDto;
import com.sda.karkonoszehiking.model.dto.WaypointDto;
import com.sda.karkonoszehiking.model.entity.WaypointEntity;
import com.sda.karkonoszehiking.repository.HikeRepository;
import com.sda.karkonoszehiking.repository.WaypointRepository;
import com.sda.karkonoszehiking.service.HikeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/hiking")
public class HikingController {

    private final HikeRepository hikeRepository;
    private final HikeService hikeService;
    private final WaypointRepository waypointRepository;

    public HikingController(HikeRepository hikeRepository, HikeService hikeService, WaypointRepository waypointRepository) {
        this.hikeRepository = hikeRepository;
        this.hikeService = hikeService;
        this.waypointRepository = waypointRepository;
    }


    //main menu
    @GetMapping
    public String mainMenu(Model model){
        model.addAttribute("name", "Karkonosze Hiking App :)");
        return "main";
    }
    @GetMapping("/all")
    public String all (Model model){
        List<HikeDto> hikesFromDb = hikeService.getHikes();
        model.addAttribute("hikesFromDb", hikesFromDb);
        return "all";
    }
    @GetMapping("/visited")
    public String visited(Model model){
        List<WaypointDto> waypointsFromDb = hikeService.getWaypoints();
        model.addAttribute("waypointsFromDb", waypointsFromDb);
        return "visited";
    }
    @GetMapping("/unvisited")
    public String unvisited(Model model){
        List<WaypointDto> unvisitedWaypointsFromDb = hikeService.getUnvisitedWaypoints();
        model.addAttribute("unvisitedWaypointsFromDb",unvisitedWaypointsFromDb);
        return "unvisited";
    }
    @GetMapping("/delete")
    public String deleteHike(){
        return "delete";
    }
    @PostMapping("/delete")
    public String delete(@RequestParam Long id, Model model){
        model.addAttribute("id", id);
        hikeService.deleteById(id);
        return "main";
    }
    @GetMapping("/add")
    public String add(Model model){
        List<WaypointEntity>allWaypoints = waypointRepository.findAll();
        Map<String, List<String>>allWaypointsPaths = new HashMap<>();
        for (WaypointEntity waypoint : allWaypoints){
            allWaypointsPaths.put(waypoint.getName(), waypoint.getAvailablePaths().stream().map(each -> each.getName()).collect(Collectors.toList()));
        }

        System.out.println(allWaypointsPaths);
        System.out.println("%$%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println(allWaypointsPaths.get("Piechowice"));
        System.out.println(allWaypointsPaths instanceof Map);
        model.addAttribute("allWaypoints", allWaypoints);
        model.addAttribute("allWaypointsPaths", allWaypointsPaths);
        return "add";
    }

/*    @PostMapping("/submit")
    public String submit(@RequestParam )*/
}
