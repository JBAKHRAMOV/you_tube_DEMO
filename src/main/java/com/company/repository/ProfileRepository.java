package com.company.repository;

import com.company.entity.ProfileEntity;
import com.company.enums.ProfileStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {
    Optional<ProfileEntity> findByEmail(String email);

    Page<ProfileEntity> findByStatus(ProfileStatus status, Pageable pageable);

    Optional<ProfileEntity> findByEmailAndPassword(String email, String password);



    @Transactional
    @Modifying
    @Query("update ProfileEntity set status = :status where id = :id")
    int updateStatus(@Param("status") ProfileStatus status, @Param("id") Integer id);
}