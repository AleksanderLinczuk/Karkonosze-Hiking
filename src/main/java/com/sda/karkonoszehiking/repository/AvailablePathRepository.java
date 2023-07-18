package com.sda.karkonoszehiking.repository;

import com.sda.karkonoszehiking.model.entity.AvailablePathsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailablePathRepository extends JpaRepository<AvailablePathsEntity, Long> {
}
