package com.ssafy.tripchat.reservation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.tripchat.reservation.domain.ParkingLots;

@Repository
public interface ParkingLotsRepository extends JpaRepository<ParkingLots, Integer> {

    @Override
	Optional<ParkingLots> findById(Integer id);
}
