package com.sushma.olxadvertise.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sushma.olxadvertise.client.LoginServiceClient;
import com.sushma.olxadvertise.client.helper.MasterdataClientHelper;
import com.sushma.olxadvertise.dto.AdvertiseRequestDto;
import com.sushma.olxadvertise.dto.AdvertiseResponseDto;
import com.sushma.olxadvertise.dto.SearchCriteriaDto;
import com.sushma.olxadvertise.entity.AdvertiseEntity;
import com.sushma.olxadvertise.exception.AdvertiseNotFoundException;
import com.sushma.olxadvertise.exception.InvalidSearchCriteriaException;
import com.sushma.olxadvertise.exception.InvalidTokenException;
import com.sushma.olxadvertise.repository.AdvertiseRepository;

@Service
public class AdvertiseServiceImpl implements AdvertiseService {

    @Autowired
    private AdvertiseRepository advertiseRepository;

    @Autowired
    private LoginServiceClient loginServiceClient;

    @Autowired
    private MasterdataClientHelper masterdataClientHelper;

    // -----------------------------------------------------------------------
    // Endpoint 8: POST /advertise
    // -----------------------------------------------------------------------
    @Override
    public AdvertiseResponseDto postAdvertise(String authToken, AdvertiseRequestDto request) {
        checkToken(authToken);
        Map<String, Object> userDetails = loginServiceClient.getUserDetails(authToken);

        String username = (String) userDetails.get("userName");
        String postedBy = userDetails.get("firstName") + " " + userDetails.get("lastName");
        int openStatusId = masterdataClientHelper.getOpenStatusId();

        AdvertiseEntity advertise = new AdvertiseEntity();
        advertise.setTitle(request.getTitle());
        advertise.setPrice(request.getPrice());
        advertise.setCategoryId(request.getCategoryId());
        advertise.setDescription(request.getDescription());
        advertise.setStatusId(openStatusId);
        advertise.setUsername(username);
        advertise.setPostedBy(postedBy);
        advertise.setCreatedDate(LocalDate.now());
        advertise.setModifiedDate(LocalDate.now());
        advertise.setActive("1");

        AdvertiseEntity saved = advertiseRepository.save(advertise);
        return toDto(saved);
    }

    // -----------------------------------------------------------------------
    // Endpoint 9: PUT /advertise/{id}
    // -----------------------------------------------------------------------
    @Override
    public AdvertiseResponseDto updateAdvertise(String authToken, int advertiseId, AdvertiseRequestDto request) {
        checkToken(authToken);
        Map<String, Object> userDetails = loginServiceClient.getUserDetails(authToken);
        String username = (String) userDetails.get("userName");

        AdvertiseEntity existing = advertiseRepository
                .findByIdAndUsernameAndActive(advertiseId, username, "1")
                .orElseThrow(() -> new AdvertiseNotFoundException(advertiseId));

        existing.setTitle(request.getTitle());
        existing.setPrice(request.getPrice());
        existing.setCategoryId(request.getCategoryId());
        existing.setDescription(request.getDescription());
        existing.setModifiedDate(LocalDate.now());

        if (request.getStatusId() != null) {
            existing.setStatusId(request.getStatusId());
        }

        AdvertiseEntity updated = advertiseRepository.save(existing);
        return toDto(updated);
    }

    // -----------------------------------------------------------------------
    // Endpoint 10: GET /user/advertise
    // -----------------------------------------------------------------------
    @Override
    public List<AdvertiseResponseDto> getMyAdvertises(String authToken) {
        checkToken(authToken);
        Map<String, Object> userDetails = loginServiceClient.getUserDetails(authToken);
        String username = (String) userDetails.get("userName");

        return advertiseRepository.findByUsernameAndActive(username, "1")
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // -----------------------------------------------------------------------
    // Endpoint 11: GET /user/advertise/{advertiseId}
    // -----------------------------------------------------------------------
    @Override
    public AdvertiseResponseDto getMyAdvertiseById(String authToken, int advertiseId) {
        checkToken(authToken);
        Map<String, Object> userDetails = loginServiceClient.getUserDetails(authToken);
        String username = (String) userDetails.get("userName");

        AdvertiseEntity advertise = advertiseRepository
                .findByIdAndUsernameAndActive(advertiseId, username, "1")
                .orElseThrow(() -> new AdvertiseNotFoundException(advertiseId));

        return toDto(advertise);
    }

    // -----------------------------------------------------------------------
    // Endpoint 12: DELETE /user/advertise/{advertiseId}
    // -----------------------------------------------------------------------
    @Override
    public boolean deleteMyAdvertise(String authToken, int advertiseId) {
        checkToken(authToken);
        Map<String, Object> userDetails = loginServiceClient.getUserDetails(authToken);
        String username = (String) userDetails.get("userName");

        AdvertiseEntity advertise = advertiseRepository
                .findByIdAndUsernameAndActive(advertiseId, username, "1")
                .orElseThrow(() -> new AdvertiseNotFoundException(advertiseId));

        // Soft-delete: mark active = "0"
        advertise.setActive("0");
        advertise.setModifiedDate(LocalDate.now());
        advertiseRepository.save(advertise);
        return true;
    }

    // -----------------------------------------------------------------------
    // Endpoint 13: GET /advertise/search/filtercriteria
    // -----------------------------------------------------------------------
    @Override
    public List<AdvertiseResponseDto> searchWithFilterCriteria(SearchCriteriaDto criteria) {
        Integer categoryId = masterdataClientHelper.getCategoryId(criteria.getCategory());

        String searchText    = isBlank(criteria.getSearchText()) ? null : criteria.getSearchText();
        String postedBy      = isBlank(criteria.getPostedBy())   ? null : criteria.getPostedBy();
        String dateCondition = criteria.getDateCondition();

        List<AdvertiseEntity> results;

        if (isBlank(dateCondition)) {
            results = advertiseRepository.filterByCriteria(searchText, categoryId, postedBy);
        } else {
            switch (dateCondition.toLowerCase()) {
                case "equals":
                    if (isBlank(criteria.getOnDate())) {
                        throw new InvalidSearchCriteriaException(
                                "onDate is required when dateCondition is 'equals'.");
                    }
                    results = advertiseRepository.filterByCriteriaOnDate(
                            searchText, categoryId, postedBy,
                            LocalDate.parse(criteria.getOnDate()));
                    break;

                case "greaterthan":
                    if (isBlank(criteria.getFromDate())) {
                        throw new InvalidSearchCriteriaException(
                                "fromDate is required when dateCondition is 'greaterthan'.");
                    }
                    results = advertiseRepository.filterByCriteriaGreaterThan(
                            searchText, categoryId, postedBy,
                            LocalDate.parse(criteria.getFromDate()));
                    break;

                case "lessthan":
                    if (isBlank(criteria.getFromDate())) {
                        throw new InvalidSearchCriteriaException(
                                "fromDate is required when dateCondition is 'lessthan'.");
                    }
                    results = advertiseRepository.filterByCriteriaLessThan(
                            searchText, categoryId, postedBy,
                            LocalDate.parse(criteria.getFromDate()));
                    break;

                case "between":
                    if (isBlank(criteria.getFromDate()) || isBlank(criteria.getToDate())) {
                        throw new InvalidSearchCriteriaException(
                                "fromDate and toDate are both required when dateCondition is 'between'.");
                    }
                    results = advertiseRepository.filterByCriteriaBetween(
                            searchText, categoryId, postedBy,
                            LocalDate.parse(criteria.getFromDate()),
                            LocalDate.parse(criteria.getToDate()));
                    break;

                default:
                    throw new InvalidSearchCriteriaException(
                            "Invalid dateCondition '" + dateCondition
                            + "'. Valid values: equals, greaterthan, lessthan, between.");
            }
        }

        // Apply sortBy
        if (!isBlank(criteria.getSortBy())) {
            String sortField = criteria.getSortBy().toLowerCase();
            if ("price".equals(sortField)) {
                results.sort((a, b) -> Double.compare(a.getPrice(), b.getPrice()));
            } else if ("date".equals(sortField) || "createddate".equals(sortField)) {
                results.sort((a, b) -> {
                    if (a.getCreatedDate() == null) return 1;
                    if (b.getCreatedDate() == null) return -1;
                    return a.getCreatedDate().compareTo(b.getCreatedDate());
                });
            }
        }

        // Apply pagination
        int start   = Math.max(criteria.getStartIndex(), 0);
        int records = criteria.getRecords() > 0 ? criteria.getRecords() : results.size();
        int end     = Math.min(start + records, results.size());
        results = start >= results.size() ? Collections.emptyList() : results.subList(start, end);

        return results.stream().map(this::toDto).collect(Collectors.toList());
    }

    // -----------------------------------------------------------------------
    // Endpoint 14: GET /advertise/search
    // -----------------------------------------------------------------------
    @Override
    public List<AdvertiseResponseDto> searchByText(String searchText) {
        if (isBlank(searchText)) {
            throw new InvalidSearchCriteriaException("searchText query parameter is required.");
        }
        return advertiseRepository.searchByText(searchText)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // -----------------------------------------------------------------------
    // Endpoint 15: GET /advertise/{advertiseId}
    // -----------------------------------------------------------------------
    @Override
    public AdvertiseResponseDto getAdvertiseById(String authToken, int advertiseId) {
        checkToken(authToken);

        AdvertiseEntity advertise = advertiseRepository.findById(advertiseId)
                .filter(a -> "1".equals(a.getActive()))
                .orElseThrow(() -> new AdvertiseNotFoundException(advertiseId));

        return toDto(advertise);
    }

    // -----------------------------------------------------------------------
    // Private: validate token via Feign; throw InvalidTokenException on failure
    // The Feign fallback already throws when the login service is unreachable.
    // This guard covers the case where the service is up but returns false.
    // -----------------------------------------------------------------------
    private void checkToken(String authToken) {
        Boolean valid = loginServiceClient.validateToken(authToken);
        if (valid == null || !valid) {
            throw new InvalidTokenException("Invalid or expired authorization token.");
        }
    }

    // -----------------------------------------------------------------------
    // Private: null/blank check — Java-8-safe replacement for String.isBlank()
    // -----------------------------------------------------------------------
    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    // -----------------------------------------------------------------------
    // Private: entity -> DTO (resolves category & status names via masterdata)
    // -----------------------------------------------------------------------
    private AdvertiseResponseDto toDto(AdvertiseEntity advertise) {
        String categoryName = masterdataClientHelper.getCategoryName(advertise.getCategoryId());
        String statusName   = masterdataClientHelper.getStatusName(advertise.getStatusId());

        AdvertiseResponseDto dto = new AdvertiseResponseDto();
        dto.setId(advertise.getId());
        dto.setTitle(advertise.getTitle());
        dto.setPrice(advertise.getPrice());
        dto.setCategory(categoryName);
        dto.setDescription(advertise.getDescription());
        dto.setUsername(advertise.getUsername());
        dto.setPostedBy(advertise.getPostedBy());
        dto.setCreatedDate(advertise.getCreatedDate());
        dto.setModifiedDate(advertise.getModifiedDate());
        dto.setStatus(statusName);
        return dto;
    }
}
