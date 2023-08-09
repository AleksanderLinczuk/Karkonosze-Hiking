package com.sda.karkonoszehiking.views;

import com.sda.karkonoszehiking.model.dto.WaypointDto;
import com.sda.karkonoszehiking.service.HikeService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Route(value = "visited", layout = MainLayout.class)
@PageTitle("Visited waypoints")
@PermitAll
public class VisitedWaypointsView extends HorizontalLayout {

    private final HikeService hikeService;
    Image map = new Image();
    Grid<WaypointDto> visitedWaypointsGrid = new Grid<>(WaypointDto.class);



    public VisitedWaypointsView(HikeService hikeService) {
        this.hikeService = hikeService;

        setSizeFull();
        configureGrid();

        add(
                getImage(),
                visitedWaypointsGrid
        );


    }

    private Component getImage() {
        map.setSrc("https://naszeszlaki.com.pl/wp-content/uploads/2017/10/4-87.jpg");
        map.setMaxWidth(100, Unit.PERCENTAGE);
        HorizontalLayout image = new HorizontalLayout(map);
        image.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        return image;
    }


    private void configureGrid() {

        visitedWaypointsGrid.addClassName("visited-grid");
        visitedWaypointsGrid.setSizeFull();

        List<WaypointDto> visitedWaypoints = hikeService.getVisitedWaypoints().stream().distinct().sorted(Comparator.comparing(WaypointDto::getName)).collect(Collectors.toList());

        visitedWaypointsGrid.setItems(visitedWaypoints);
        visitedWaypointsGrid.removeAllColumns();

        visitedWaypointsGrid.addColumn(w -> visitedWaypoints.indexOf(w)+1).setHeader("Waypoint No");
        visitedWaypointsGrid.addColumn(WaypointDto::getName).setHeader("Name");
        visitedWaypointsGrid.addColumn(WaypointDto::getHeight).setHeader(("Height"));

        visitedWaypointsGrid.getColumns().forEach(each -> each.setAutoWidth(true));
        visitedWaypointsGrid.setMaxWidth(550,Unit.PIXELS);
    }


}
