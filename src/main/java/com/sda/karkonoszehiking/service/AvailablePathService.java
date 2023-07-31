package com.sda.karkonoszehiking.service;

import com.sda.karkonoszehiking.model.entity.AvailablePathsEntity;
import com.sda.karkonoszehiking.repository.AvailablePathRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class AvailablePathService {
    private final AvailablePathRepository availablePathRepository;

    public AvailablePathService(AvailablePathRepository availablePathRepository) {
        this.availablePathRepository = availablePathRepository;
    }

    public Optional<AvailablePathsEntity> findAvailablePathsEntityByName(String name) {
        return availablePathRepository.findAvailablePathsEntityByName(name);
    }
}
