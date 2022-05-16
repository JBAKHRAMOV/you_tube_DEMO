package com.company.repository;

import com.company.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoRepository extends JpaRepository<VideoEntity, Integer> {
    Optional<VideoEntity> findByProfileIdAndKey(Integer pId, String key);
}