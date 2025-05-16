package com.ssafy.tripchat.travel;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.tripchat.member.domain.Members;
import com.ssafy.tripchat.travel.domain.Attractions;
import com.ssafy.tripchat.travel.domain.AttractionsSpecification;
import com.ssafy.tripchat.travel.dto.AttractionSearchCondition;
import com.ssafy.tripchat.travel.repository.AttractionsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttractionService {
	
	private final AttractionsRepository attractionsRepository;
	
    public List<Attractions> searchByCondition(AttractionSearchCondition searchCondition) {
    	int regionCode = searchCondition.getRegionCode();
    	searchCondition.setMetropolitanCode(regionCode / 1000);
    	searchCondition.setLocalCode(regionCode % 1000);
    	
//    	if (searchCondition.isRangeSearch()) {
//    		return attractionsRepository.findAll(AttractionsSpecification.filterByConditions(
//    				searchCondition.getMetropolitanCode(),
//    				searchCondition.getLocalCode(),
//    				searchCondition.getContentTypeId()));
//    	}
        return null;
    }
}
