package com.company.entity;

import com.company.enums.LikeType;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Data
public class VideoLikeEntity {
//    id,profile_id,video_id,created_date,type(Like,Dislike)
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
