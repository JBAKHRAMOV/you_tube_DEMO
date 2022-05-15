package com.company.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private LocalDateTime createDate=LocalDateTime.now();
    @Column
    private LocalDateTime updateDate;
}
