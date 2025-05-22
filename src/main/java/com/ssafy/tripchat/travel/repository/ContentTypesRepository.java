package com.ssafy.tripchat.travel.repository;

import com.ssafy.tripchat.travel.domain.ContentTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentTypesRepository extends JpaRepository<ContentTypes, Integer> {
}
