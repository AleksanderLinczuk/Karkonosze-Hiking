package com.sda.karkonoszehiking.controller;


import com.sda.karkonoszehiking.model.dto.HikeDto;
import com.sda.karkonoszehiking.model.dto.WaypointDto;
import com.sda.karkonoszehiking.model.entity.HikeEntity;
import com.sda.karkonoszehiking.repository.HikeRepository;
import com.sda.karkonoszehiking.service.HikeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/hiking")
public class HikingController {

    private final HikeRepository hikeRepository;
    private final HikeService hikeService;

    public HikingController(HikeRepository hikeRepository, HikeService hikeService) {
        this.hikeRepository = hikeRepository;
        this.hikeService = hikeService;
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
}
