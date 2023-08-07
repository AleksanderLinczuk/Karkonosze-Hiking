package com.sda.karkonoszehiking.views;

import com.sda.karkonoszehiking.model.dto.WaypointDto;
import com.sda.karkonoszehiking.service.HikeService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Route(value = "visited", layout = MainLayout.class)
@PageTitle("Visited waypoints")
public class VisitedWaypointsView extends VerticalLayout {

    private final HikeService hikeService;
    Grid<WaypointDto> visitedWaypointsGrid = new Grid<>(WaypointDto.class);



    public VisitedWaypointsView(HikeService hikeService) {
        this.hikeService = hikeService;

        setSizeFull();
        configureGrid();


        add(visitedWaypointsGrid);


    }



    private void configureGrid() {

        visitedWaypointsGrid.addClassName("visited-grid");
        visitedWaypointsGrid.setSizeFull();

        List<WaypointDto> visitedWaypoints = hikeService.getWaypoints().stream().distinct().sorted(Comparator.comparing(WaypointDto::getName)).collect(Collectors.toList());

        visitedWaypointsGrid.setItems(visitedWaypoints);
        visitedWaypointsGrid.removeAllColumns();

        visitedWaypointsGrid.addColumn(w -> visitedWaypoints.indexOf(w)+1).setHeader("Waypoint No");
        visitedWaypointsGrid.addColumn(WaypointDto::getName).setHeader("Name");
        visitedWaypointsGrid.addColumn(WaypointDto::getHeight).setHeader(("Height"));

        visitedWaypointsGrid.getColumns().forEach(each -> each.setAutoWidth(true));
    }


}
