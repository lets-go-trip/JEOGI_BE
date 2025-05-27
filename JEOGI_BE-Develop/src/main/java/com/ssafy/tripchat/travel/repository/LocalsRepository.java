package com.ssafy.tripchat.travel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.tripchat.travel.domain.Locals;

@Repository
public interface LocalsRepository extends JpaRepository<Locals, Integer> {
    List<Locals> findAllByMetropolitanCode(Integer metropolitanCode);
}
