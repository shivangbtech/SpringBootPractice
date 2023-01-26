package com.irdeto.jpa.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Student {
    @Id
    private long id;
    private String name;
    private int testScore;
}
