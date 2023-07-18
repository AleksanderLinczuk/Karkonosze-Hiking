package com.sda.karkonoszehiking.controller;


import com.sda.karkonoszehiking.repository.HikeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hiking")
public class HikingController {

    private final HikeRepository hikeRepository;

    public HikingController(HikeRepository hikeRepository) {
        this.hikeRepository = hikeRepository;
    }


    //main menu
    @GetMapping
    public String mainMenu(Model model){
        model.addAttribute("name", "Karkonosze Hiking App :)");
        return "main";
    }
}
