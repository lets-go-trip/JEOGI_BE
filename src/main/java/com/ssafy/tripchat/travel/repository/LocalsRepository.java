package com.ssafy.tripchat.travel.repository;

import com.ssafy.tripchat.travel.domain.Locals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalsRepository extends JpaRepository<Locals, Integer> {
}
