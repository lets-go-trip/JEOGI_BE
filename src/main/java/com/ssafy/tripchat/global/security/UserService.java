package com.ssafy.tripchat.global.security;

import com.ssafy.tripchat.member.domain.Members;
import com.ssafy.tripchat.member.domain.MembersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final MembersRepository membersRepository;
    private final PasswordEncoder passwordEncoder;

    public Members registerUser(Members member) {
        if (membersRepository.existsByUsername(member.getUsername())) {
            throw new RuntimeException("이미 존재하는 사용자입니다.");
        }

        member.setPassword(passwordEncoder.encode(member.getPassword()));
        return membersRepository.save(member);
    }

    public Members findById(String id) {
        return membersRepository.findByUsername(id)
                .orElseThrow(() -> new UsernameNotFoundException("ID not found: " + id));
    }
}