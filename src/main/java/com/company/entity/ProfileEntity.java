package com.company.entity;

import com.company.enums.GeneralStatus;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table
@Entity
@Setter
@Getter
public class ProfileEntity extends BaseEntity {

    //id,name,surname,email,photo,role,status

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column
    @Enumerated(EnumType.STRING)
    private ProfileRole role;
    @Column
    @Enumerated(EnumType.STRING)
    private ProfileStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attach_id")
    private AttachEntity attach;
}
