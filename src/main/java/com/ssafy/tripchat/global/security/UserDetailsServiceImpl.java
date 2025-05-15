package com.ssafy.tripchat.global.security;

import com.ssafy.tripchat.global.security.domain.MemberPrincipal;
import com.ssafy.tripchat.member.domain.Members;
import com.ssafy.tripchat.member.domain.MembersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//TODO : 임시 수정을 위한 주석처리
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MembersRepository membersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Members member = membersRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("ID not found: " + username));
        
        return new MemberPrincipal(member);
    }

}
