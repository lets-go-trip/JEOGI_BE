package com.ssafy.tripchat.global.security.dto.response;

import com.ssafy.tripchat.global.security.domain.MemberPrincipal;

public record LoginResponse(String message, MemberInfo user) {
    public static LoginResponse success(MemberPrincipal member) {
        return new LoginResponse("로그인 성공", new MemberInfo(member.getUsername()));
    }

    public static LoginResponse failure() {
        return new LoginResponse("로그인 실패", null);
    }

    public record MemberInfo(String username) {
    }
}
