package com.company.repository;

import com.company.entity.ChannelEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChannelRepository extends JpaRepository<ChannelEntity, Integer> {
    Optional<ChannelEntity> findByName(String name);
    Optional<ChannelEntity> findByProfileIdAndKey(Integer pId,String key);
    Optional<ChannelEntity> findByProfileIdAndId(Integer pId,Integer integer);
    Page<ChannelEntity> findByProfileId(Integer pId, Pageable pageable);
    Optional<ChannelEntity> findByKey(String key);
}