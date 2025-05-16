package com.ssafy.tripchat.travel.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Locals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int metropolitanCode;
    private int code;
    private String name;

    public Locals(int metropolitanCode, int code, String name) {
        this.metropolitanCode = metropolitanCode;
        this.code = code;
        this.name = name;
    }
}
