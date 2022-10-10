package com.company.entity;

import com.company.enums.LikeType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Setter
@Getter
public class VideoLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "profile_id")
    private Integer profileId;
    @ManyToOne
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;

    @Column(name = "video_id")
    private String videoKey;
    @ManyToOne
    @JoinColumn(name = "video_id")
    private VideoEntity video;

    @Column
    @Enumerated(EnumType.STRING)
    private LikeType type;

    @Column
    private LocalDateTime createdDate=LocalDateTime.now();

}
