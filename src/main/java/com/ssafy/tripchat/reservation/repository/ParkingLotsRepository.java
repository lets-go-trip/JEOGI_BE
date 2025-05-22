package com.ssafy.tripchat.reservation.repository;

import com.ssafy.tripchat.reservation.domain.ParkingLots;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotsRepository extends JpaRepository<ParkingLots, Integer> {

    Optional<ParkingLots> findById(Integer id);
}
