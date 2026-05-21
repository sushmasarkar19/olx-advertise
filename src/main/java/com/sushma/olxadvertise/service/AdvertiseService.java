package com.sushma.olxadvertise.service;

import java.util.List;

import com.sushma.olxadvertise.dto.AdvertiseRequestDto;
import com.sushma.olxadvertise.dto.AdvertiseResponseDto;
import com.sushma.olxadvertise.dto.SearchCriteriaDto;

public interface AdvertiseService {

    // Endpoint 8: POST /advertise
    AdvertiseResponseDto postAdvertise(String authToken, AdvertiseRequestDto request);

    // Endpoint 9: PUT /advertise/{id}
    AdvertiseResponseDto updateAdvertise(String authToken, int advertiseId, AdvertiseRequestDto request);

    // Endpoint 10: GET /user/advertise
    List<AdvertiseResponseDto> getMyAdvertises(String authToken);

    // Endpoint 11: GET /user/advertise/{advertiseId}
    AdvertiseResponseDto getMyAdvertiseById(String authToken, int advertiseId);

    // Endpoint 12: DELETE /user/advertise/{advertiseId}
    boolean deleteMyAdvertise(String authToken, int advertiseId);

    // Endpoint 13: GET /advertise/search/filtercriteria
    List<AdvertiseResponseDto> searchWithFilterCriteria(SearchCriteriaDto criteria);

    // Endpoint 14: GET /advertise/search
    List<AdvertiseResponseDto> searchByText(String searchText);

    // Endpoint 15: GET /advertise/{advertiseId}
    AdvertiseResponseDto getAdvertiseById(String authToken, int advertiseId);
}
