package com.sda.karkonoszehiking.repository;

import com.sda.karkonoszehiking.model.entity.HikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HikeRepository extends JpaRepository<HikeEntity, Long> {
}
