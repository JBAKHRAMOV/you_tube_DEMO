package com.company.entity;

import com.company.enums.VideoStatus;
import com.company.enums.VideoType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table
public class VideoEntity extends BaseEntity{

    @Column
    private UUID key;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    @Enumerated(EnumType.STRING)
    private VideoStatus status;

    @Column
    @Enumerated(EnumType.STRING)
    private VideoType type;

    @Column
    private Integer viewCount;

    @Column
    private Integer sharedCount;

    @Column(name = "preview_attach_id")
    private String previewAttachId;
    @OneToOne
    @JoinColumn(name = "preview_attach_id", insertable = false, updatable = false)
    private AttachEntity previewAttach;

    @Column(name = "category_id")
    private Integer categoryId;
    @ManyToOne
    @JoinColumn(name = "category_id",insertable = false, updatable = false)
    private CategoryEntity category;

    @Column(name = "video_id")
    private String videoId;
    @ManyToOne
    @JoinColumn(name = "video_id",insertable = false,updatable = false)
    private AttachEntity videoAttach;

    @Column(name = "channel_id")
    private Integer channelId;
    @ManyToOne
    @JoinColumn(name = "channel_id",insertable = false,updatable = false)
    private  ChannelEntity channel;





}
