package com.sda.karkonoszehiking.views;

import com.sda.karkonoszehiking.model.dto.WaypointDto;
import com.sda.karkonoszehiking.service.HikeService;
import com.sda.karkonoszehiking.service.WaypointService;
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

@Route(value = "unvisited", layout = MainLayout.class)
@PageTitle("Unvisited waypoints")
@PermitAll
public class UnvisitedWaypointsView extends HorizontalLayout {

    private final HikeService hikeService;
    private final WaypointService waypointService;
    Image map = new Image();
    Grid<WaypointDto> unvisitedWaypointsGrid = new Grid<>(WaypointDto.class);



    public UnvisitedWaypointsView(HikeService hikeService, WaypointService waypointService) {
        this.hikeService = hikeService;
        this.waypointService = waypointService;

        setSizeFull();
        configureGrid();


        add(
                getImage(),
                unvisitedWaypointsGrid
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
        unvisitedWaypointsGrid.setMaxWidth(550,Unit.PIXELS);

    }


}
