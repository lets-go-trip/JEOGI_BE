package com.ssafy.tripchat.travel.repository;

import com.ssafy.tripchat.travel.domain.Attractions;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttractionsRepository extends JpaRepository<Attractions, Integer> {
	
}
