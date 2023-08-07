package com.sda.karkonoszehiking.views;

import com.sda.karkonoszehiking.model.dto.WaypointDto;
import com.sda.karkonoszehiking.service.HikeService;
import com.sda.karkonoszehiking.service.WaypointService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Comparator;
import java.util.List;

@Route(value = "unvisited", layout = MainLayout.class)
@PageTitle("Unvisited waypoints")
public class UnvisitedWaypointsView extends VerticalLayout {

    private final HikeService hikeService;
    private final WaypointService waypointService;
    Grid<WaypointDto> unvisitedWaypointsGrid = new Grid<>(WaypointDto.class);



    public UnvisitedWaypointsView(HikeService hikeService, WaypointService waypointService) {
        this.hikeService = hikeService;
        this.waypointService = waypointService;

        setSizeFull();
        configureGrid();


        add(unvisitedWaypointsGrid);


    }



    private void configureGrid() {

        unvisitedWaypointsGrid.addClassName("unvisited-grid");
        unvisitedWaypointsGrid.setSizeFull();

        List<WaypointDto> unvisitedWaypoints = hikeService.getUnvisitedWaypoints();
        unvisitedWaypoints.sort(Comparator.comparing(WaypointDto::getName));

        unvisitedWaypointsGrid.setItems(unvisitedWaypoints);
        unvisitedWaypointsGrid.removeAllColumns();

        unvisitedWaypointsGrid.addColumn(w -> unvisitedWaypoints.indexOf(w)+1).setHeader("Waypoint No");
        unvisitedWaypointsGrid.addColumn(WaypointDto::getName).setHeader("Name");
        unvisitedWaypointsGrid.addColumn(WaypointDto::getHeight).setHeader(("Height"));

        unvisitedWaypointsGrid.getColumns().forEach(each -> each.setAutoWidth(true));
    }


}
