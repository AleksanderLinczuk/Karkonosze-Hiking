package com.sda.karkonoszehiking.views;

import com.sda.karkonoszehiking.model.dto.HikeDto;
import com.sda.karkonoszehiking.service.HikeService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route("hey")
public class AllHikesGui extends VerticalLayout {

   @Autowired
    public AllHikesGui(HikeService hikeService) {
        Grid<HikeDto> grid = new Grid<>(HikeDto.class);
       List<HikeDto> hikes = hikeService.getHikes();
       Hibernate.initialize(hikeService.getRoutes());
       grid.setItems(hikes);
        add(grid);
    }
}
