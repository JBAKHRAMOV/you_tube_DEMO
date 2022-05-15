package com.company.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tag")
@Getter
@Setter
public class TagEntity {
    //id,name,created_date
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name",nullable = false, unique = true)
    private String name;
    @CreationTimestamp
    private LocalDateTime createdDate;
}


