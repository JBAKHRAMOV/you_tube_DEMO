package com.company.entity;

import com.company.enums.GeneralStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table
@Entity
@Setter
@Getter
public class ChannelEntity extends BaseEntity {
    @Column(unique = true)
    private String name;
    @Column
    private GeneralStatus status;
    @Column
    private String description;
    @Column
    String key;

    @Column(nullable = false)
    private Integer profileId;
    @ManyToOne()
    @JoinColumn(insertable = false, updatable = false)
    private ProfileEntity profile;


    @OneToOne()
    @JoinColumn()
    private AttachEntity banner;

    @OneToOne()
    @JoinColumn()
    private AttachEntity photo;
}
