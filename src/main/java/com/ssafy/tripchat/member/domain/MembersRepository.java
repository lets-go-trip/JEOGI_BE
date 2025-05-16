package com.ssafy.tripchat.member.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembersRepository extends JpaRepository<Members, Integer> {
    Optional<Members> findByUsername(String id);

    boolean existsByUsername(String id);
}