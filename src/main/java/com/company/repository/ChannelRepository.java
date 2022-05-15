package com.company.repository;

import com.company.entity.ChannelEntity;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChannelRepository extends JpaRepository<ChannelEntity, Integer> {
    Optional<ChannelEntity> findByName(String name);
=======
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface ChannelRepository extends JpaRepository<ChannelEntity, Integer> {
    Optional<ChannelEntity> findByName(String name);
    Optional<ChannelEntity> findByProfileIdAndKey(Integer pId,String key);
    Page<ChannelEntity> findByProfileId(Integer pId, Pageable pageable);
    Optional<ChannelEntity> findByKey(String key);
>>>>>>> 243834d (Initial commit)
}