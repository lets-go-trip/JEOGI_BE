package com.ssafy.tripchat.member.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	private int id;
	private String email;
	private String password;
	private String username;
	private String role;
}
