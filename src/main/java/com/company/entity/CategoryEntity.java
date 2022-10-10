package com.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table()
public class CategoryEntity extends BaseEntity{
    @Column(nullable = false, unique = true)
    private String name;
}
