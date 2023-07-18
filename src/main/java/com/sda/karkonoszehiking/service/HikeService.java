package com.sda.karkonoszehiking.service;

import com.sda.karkonoszehiking.model.dto.HikeDto;
import com.sda.karkonoszehiking.model.entity.HikeEntity;
import com.sda.karkonoszehiking.model.entity.RouteEntity;
import com.sda.karkonoszehiking.repository.HikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class HikeService {

    private final HikeRepository hikeRepository;

    public HikeService(HikeRepository hikeRepository) {
        this.hikeRepository = hikeRepository;
    }

    public List<HikeDto> getHikes() {
        return hikeRepository.findAll().stream().map(hike -> new HikeDto(hike.getHikeId(), hike.getDate(), hike.getDuration(), hike.getRoutes(), getHikeWaypoints(hike.getHikeId()) )).collect(Collectors.toList());
    }

 /*   public List<String> getHikeWaypoints() {
        List<RouteEntity> routes = hikeRepository.findAll().stream().flatMap(hike -> hike.getRoutes().stream()).collect(Collectors.toList());
        List<String> names = routes.stream().map(route -> route.getStart().getName()+" -> " + route.getEnd().getName()+" -> ").collect(Collectors.toList());
        return names;
    }*/
    public String getHikeWaypoints(Long hikeId){
        return hikeRepository.findById(hikeId).get().getRoutes().stream().map(route -> route.getStart().getName() + " -> " + route.getEnd().getName()).collect(Collectors.joining(" -> "));
    }
}
