package com.ssafy.tripchat.travel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.tripchat.travel.domain.ContentTypes;

@Repository
public interface ContentTypesRepository extends JpaRepository<ContentTypes, Integer> {
}
