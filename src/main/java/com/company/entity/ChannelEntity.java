package com.company.entity;

import com.company.enums.GeneralStatus;
import lombok.Data;

import javax.persistence.*;

@Table
@Entity
@Data
public class ChannelEntity extends BaseEntity{
//id,name,photo,description,status (ACTIVE, BLOCK),banner,profile_id,key
    @Column(unique = true)
    private String name;
    @Column
    private GeneralStatus status;
    @Column
    private String description;
    @Column String key;

    @Column(name = "profile_id", nullable = false)
    private Integer profileId;
    @ManyToOne()
    @JoinColumn(name = "profile_id",insertable = false, updatable = false)
    private ProfileEntity profile;


    @OneToOne()
    @JoinColumn(name = "banner_id")
    private AttachEntity banner;

    @OneToOne()
    @JoinColumn(name = "photo_id")
    private AttachEntity photo;
}
