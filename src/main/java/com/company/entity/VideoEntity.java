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
    //id, created_date, published_date  *
    //key, title, description,  *
    // preview_attach_id, category_id, attach_id, channel_id, *
    // status(private,public), type(video,short)  *
    // like(like_count,dislike_count)
    // view_count , shared_count,

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

    @Column(name = "preview_attch_id")
    private String previewAttchId;
    @OneToOne
    @JoinColumn(name = "preview_attch_id")
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
