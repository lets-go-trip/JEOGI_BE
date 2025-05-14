package com.ssafy.tripchat.global.security;

import com.ssafy.tripchat.member.domain.Members;
import com.ssafy.tripchat.member.domain.MembersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MembersRepository membersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Members member = membersRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("ID not found: " + username));
        return User.builder()
                .username(member.getUsername())
                .password(member.getPassword())
                .build();
    }

}
