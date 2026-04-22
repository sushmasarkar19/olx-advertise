package com.sushma.olxadvertise.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sushma.olxadvertise.dto.AdvertiseRequestDto;
import com.sushma.olxadvertise.dto.AdvertiseResponseDto;
import com.sushma.olxadvertise.dto.SearchCriteriaDto;
import com.sushma.olxadvertise.service.AdvertiseService;

@RestController
public class AdvertisementController {

    @Autowired
    private AdvertiseService advertiseService;

    /**
     * Endpoint 8: POST /advertise
     * Posts a new advertisement. Requires Authorization header.
     * Returns: 201 Created with the created advertisement.
     */
    @PostMapping("/advertise")
    public ResponseEntity<AdvertiseResponseDto> postAdvertise(
            @RequestHeader("Authorization") String authToken,
            @RequestBody AdvertiseRequestDto request) {

        AdvertiseResponseDto response = advertiseService.postAdvertise(authToken, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Endpoint 9: PUT /advertise/{id}
     * Updates an existing advertisement. Requires Authorization header.
     * Returns: 200 OK with the updated advertisement.
     */
    @PutMapping("/advertise/{id}")
    public ResponseEntity<AdvertiseResponseDto> updateAdvertise(
            @RequestHeader("Authorization") String authToken,
            @PathVariable("id") int advertiseId,
            @RequestBody AdvertiseRequestDto request) {

        AdvertiseResponseDto response = advertiseService.updateAdvertise(authToken, advertiseId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Endpoint 10: GET /user/advertise
     * Returns all advertisements posted by the logged-in user. Requires Authorization header.
     * Returns: 200 OK with list of advertisements.
     */
    @GetMapping("/user/advertise")
    public ResponseEntity<Map<String, Object>> getMyAdvertises(
            @RequestHeader("Authorization") String authToken) {

        List<AdvertiseResponseDto> advertises = advertiseService.getMyAdvertises(authToken);
        Map<String, Object> response = new HashMap<>();
        response.put("advertises", advertises);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Endpoint 11: GET /user/advertise/{advertiseId}
     * Returns a specific advertisement posted by the logged-in user. Requires Authorization header.
     * Returns: 200 OK with the advertisement, or 404 if not found.
     */
    @GetMapping("/user/advertise/{advertiseId}")
    public ResponseEntity<AdvertiseResponseDto> getMyAdvertiseById(
            @RequestHeader("Authorization") String authToken,
            @PathVariable int advertiseId) {

        AdvertiseResponseDto response = advertiseService.getMyAdvertiseById(authToken, advertiseId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Endpoint 12: DELETE /user/advertise/{advertiseId}
     * Soft-deletes a specific advertisement posted by the logged-in user. Requires Authorization header.
     * Returns: 200 OK with true/false.
     */
    @DeleteMapping("/user/advertise/{advertiseId}")
    public ResponseEntity<Boolean> deleteMyAdvertise(
            @RequestHeader("Authorization") String authToken,
            @PathVariable int advertiseId) {

        boolean result = advertiseService.deleteMyAdvertise(authToken, advertiseId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * Endpoint 13: GET /advertise/search/filtercriteria
     * Searches advertisements with multiple filter criteria.
     * No authentication required.
     *
     * Query params: searchText, category, postedBy, dateCondition, onDate, fromDate, toDate, sortBy, startIndex, records
     * Returns: 200 OK with matching advertisements.
     */
    @GetMapping("/advertise/search/filtercriteria")
    public ResponseEntity<Map<String, Object>> searchWithFilterCriteria(
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String postedBy,
            @RequestParam(required = false) String dateCondition,
            @RequestParam(required = false) String onDate,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "0") int startIndex,
            @RequestParam(defaultValue = "0") int records) {

        SearchCriteriaDto criteria = new SearchCriteriaDto();
        criteria.setSearchText(searchText);
        criteria.setCategory(category);
        criteria.setPostedBy(postedBy);
        criteria.setDateCondition(dateCondition);
        criteria.setOnDate(onDate);
        criteria.setFromDate(fromDate);
        criteria.setToDate(toDate);
        criteria.setSortBy(sortBy);
        criteria.setStartIndex(startIndex);
        criteria.setRecords(records);

        List<AdvertiseResponseDto> advertises = advertiseService.searchWithFilterCriteria(criteria);
        Map<String, Object> response = new HashMap<>();
        response.put("advertises", advertises);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Endpoint 14: GET /advertise/search
     * Searches advertisements matching searchText in title or description.
     * No authentication required.
     *
     * Query param: searchText
     * Returns: 200 OK with matching advertisements.
     */
    @GetMapping("/advertise/search")
    public ResponseEntity<Map<String, Object>> searchByText(
            @RequestParam String searchText) {

        List<AdvertiseResponseDto> advertises = advertiseService.searchByText(searchText);
        Map<String, Object> response = new HashMap<>();
        response.put("advertises", advertises);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Endpoint 15: GET /advertise/{advertiseId}
     * Returns details of a specific advertisement. Requires Authorization header.
     * Returns: 200 OK with advertisement details, or 404 if not found.
     */
    @GetMapping("/advertise/{advertiseId}")
    public ResponseEntity<AdvertiseResponseDto> getAdvertiseById(
            @RequestHeader("Authorization") String authToken,
            @PathVariable int advertiseId) {

        AdvertiseResponseDto response = advertiseService.getAdvertiseById(authToken, advertiseId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
