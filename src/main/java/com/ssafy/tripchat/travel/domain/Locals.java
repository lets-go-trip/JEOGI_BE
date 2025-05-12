package com.ssafy.tripchat.travel.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Locals {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int metropolitanCode;
    private int code;
    private String name;
}
