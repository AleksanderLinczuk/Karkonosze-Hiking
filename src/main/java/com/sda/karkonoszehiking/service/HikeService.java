package com.sda.karkonoszehiking.service;

import com.sda.karkonoszehiking.model.dto.HikeDto;
import com.sda.karkonoszehiking.model.dto.RouteDto;
import com.sda.karkonoszehiking.model.dto.WaypointDto;
import com.sda.karkonoszehiking.model.entity.AvailablePathsEntity;
import com.sda.karkonoszehiking.model.entity.WaypointEntity;
import com.sda.karkonoszehiking.repository.AvailablePathRepository;
import com.sda.karkonoszehiking.repository.HikeRepository;
import com.sda.karkonoszehiking.repository.WaypointRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HikeService {

    private final HikeRepository hikeRepository;
    private final AvailablePathRepository availablePathRepository;
    private final WaypointRepository waypointRepository;

    public HikeService(HikeRepository hikeRepository, AvailablePathRepository availablePathRepository, WaypointRepository waypointRepository) {
        this.hikeRepository = hikeRepository;
        this.availablePathRepository = availablePathRepository;
        this.waypointRepository = waypointRepository;
    }


    public List<HikeDto> getHikes() {
        return hikeRepository.findAll().stream()
                .map(hike -> new HikeDto(hike.getHikeId(),
                        hike.getDate(), hike.getDuration(), hike.getRoutes(),
                        getHikeWaypoints(hike.getHikeId()),
                        getHikeDistance(hike.getHikeId()),
                        getPace(hike.getHikeId()),
                        getSpeed(hike.getHikeId())))
                .collect(Collectors.toList());
    }

    public List<RouteDto> getRoutes() {
        return getHikes().stream().flatMap(hike -> hike.getRoutes().stream()).collect(Collectors.toList())
                .stream().map(route -> new RouteDto(route.getRouteId(), route.getStart(), route.getEnd(), route.getLength())).collect(Collectors.toList());
    }

    //TODO make separate service classes!
    public WaypointEntity mapAvailablePathsToWaypoints(AvailablePathsEntity availablePathsEntity) {
        return waypointRepository.findById(availablePathsEntity.getAvailablePathId()).get();
    }
/*    public List<WaypointEntity>mapAvailablePathsToWaypoints(List<AvailablePathsEntity> availablePathsEntityList){
        return availablePathsEntityList.stream().map(each -> mapAvailablePathsToWaypoints(each)).collect(Collectors.toList());
    }*/


    public List<WaypointDto> getWaypoints() {
        Set<WaypointEntity> startWaypoints = getRoutes().stream().map(route -> route.getStart()).collect(Collectors.toSet());
        Set<WaypointEntity> endWaypoints = getRoutes().stream().map(route -> route.getEnd()).collect(Collectors.toSet()).stream().map(each -> mapAvailablePathsToWaypoints(each)).collect(Collectors.toSet());
        Set<WaypointEntity> result = new HashSet<>(startWaypoints);
        result.addAll(endWaypoints);
        return result.stream().map(waypoint -> new WaypointDto(waypoint.getWaypointId(), waypoint.getName(), waypoint.getHeight())).sorted(Comparator.comparing(WaypointDto::getWaypointId)).collect(Collectors.toList());
    }

    public List<WaypointDto> getUnvisitedWaypoints() {
        List<WaypointDto> allWaypoints = waypointRepository.findAll().stream().map(waypoint -> new WaypointDto(waypoint.getWaypointId(), waypoint.getName(), waypoint.getHeight())).collect(Collectors.toList());
        List<WaypointDto> visitedWaypoints = new ArrayList<>(getWaypoints());
        List<WaypointDto> result = new ArrayList<>(allWaypoints);
        result.removeAll(visitedWaypoints);
        return result;
    }


 /*   public List<String> getHikeWaypoints() {
        List<RouteEntity> routes = hikeRepository.findAll().stream().flatMap(hike -> hike.getRoutes().stream()).collect(Collectors.toList());
        List<String> names = routes.stream().map(route -> route.getStart().getName()+" -> " + route.getEnd().getName()+" -> ").collect(Collectors.toList());
        return names;
    }*/

    //todo: ADD exception handling and get rid of .get() in optionals!
    public String getHikeWaypoints(Long hikeId) {
        return hikeRepository.findById(hikeId).get().getRoutes().stream().map(route -> route.getStart().getName() + " -> " + route.getEnd().getName()).collect(Collectors.joining(" -> "));
    }

    public double getHikeDistance(Long hikeId) {
        return hikeRepository.findById(hikeId).get().getRoutes().stream().mapToDouble(route -> route.getLength()).sum();
    }

    public LocalTime getHikeDuration(Long hikeId) {
        return hikeRepository.findById(hikeId).get().getDuration();
    }

    public double calculatePace(Long hikeId) {
        LocalTime duration = getHikeDuration(hikeId);
        double distance = getHikeDistance(hikeId);
        double durationInMinutes = duration.getHour() * 60 + duration.getMinute() + (double) duration.getSecond() / 60;
        return durationInMinutes / distance;
    }

    public String getPace(Long hikeId) {
        return String.format("%.2f", calculatePace(hikeId));
    }

    public double calculateSpeed(Long hikeId) {
        LocalTime duration = getHikeDuration(hikeId);
        double distance = getHikeDistance(hikeId);
        double durationInHours = duration.getHour() + (double) duration.getMinute() / 60 + (double) duration.getSecond() / 3600;
        return distance / durationInHours;
    }

    public String getSpeed(Long hikeId) {
        return String.format("%.2f", calculateSpeed(hikeId));
    }

    public void deleteById(Long id) {
        hikeRepository.deleteById(id);
    }
}
