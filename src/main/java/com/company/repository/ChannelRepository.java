package com.company.repository;

import com.company.entity.ChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChannelRepository extends JpaRepository<ChannelEntity, Integer> {
    Optional<ChannelEntity> findByName(String name);
}