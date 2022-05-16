package com.company.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "category")
public class CategoryEntity extends BaseEntity{
//    id,name,created_date
    @Column(name = "name",nullable = false, unique = true)
    private String name;
}
