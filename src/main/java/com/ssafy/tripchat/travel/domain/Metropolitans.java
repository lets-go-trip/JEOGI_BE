package com.ssafy.tripchat.travel.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Metropolitans {

    private int id;

    @Id
    private int code;
    private String name;

    public Metropolitans(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
