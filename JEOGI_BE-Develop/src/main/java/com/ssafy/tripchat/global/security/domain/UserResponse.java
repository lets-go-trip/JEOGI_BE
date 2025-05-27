package com.ssafy.tripchat.global.security.domain;

import com.ssafy.tripchat.member.domain.Members;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String name;
    private String email;
    // private List<String> roles;

    public static UserResponse from(Members member) {
        return new UserResponse(
                member.getUsername(),
                member.getNickname(),
                member.getEmail()
        );
    }
}
