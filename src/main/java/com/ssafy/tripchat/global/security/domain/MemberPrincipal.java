package com.ssafy.tripchat.global.security.domain;

import com.ssafy.tripchat.member.domain.Members;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class MemberPrincipal implements UserDetails {
    private final int id;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public MemberPrincipal(Members m) {
        this.id = m.getId();
        this.username = m.getUsername();
        this.password = m.getPassword();

        //TODO:: USER 하드코딩 동적으로 변경
        this.authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + "USER")
        );
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }
}
