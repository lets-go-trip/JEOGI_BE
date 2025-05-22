package com.ssafy.tripchat.travel.repository;

import com.ssafy.tripchat.travel.domain.Metropolitans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetropolitansRepository extends JpaRepository<Metropolitans, Integer> {
}
