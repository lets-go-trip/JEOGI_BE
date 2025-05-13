package com.ssafy.tripchat.member.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembersRepository extends JpaRepository<Members, Integer> {
  Optional<Members> findById(String id);

  boolean existsById(String id);
}
