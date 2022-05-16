package com.company.repository;

import com.company.entity.VideoLikeEntity;
import com.company.enums.LikeType;
import com.company.enums.VideoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.List;

public interface VideoLikeRepository extends JpaRepository<VideoLikeEntity, Integer> {
    Optional<VideoLikeEntity> findByProfileIdAndVideoKey(Integer pId, String key);
    List<VideoLikeEntity> findByVideoKey(String key);

    @Transactional
    @Modifying
    @Query("update VideoLikeEntity set type = :type where id = :id")
    int updateType(@Param("type") LikeType type, @Param("id") Integer id);
}