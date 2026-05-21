package com.sushma.olxadvertise.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.sushma.olxadvertise.client.fallback.MasterdataServiceFallback;
import com.sushma.olxadvertise.dto.masterdataclient.AdvertiseStatusResponse;
import com.sushma.olxadvertise.dto.masterdataclient.CategoryResponse;
import com.sushma.olxadvertise.dto.masterdataclient.StatusListResponse;

/**
 * Feign client for olx-masterdata service.
 * 
 * Uses Eureka service discovery instead of hardcoded URLs.
 * The service name MUST match the spring.application.name in olx-masterdata's application.properties
 * 
 * Service Discovery Flow:
 * 1. Feign queries Eureka for service: "olx-masterdata"
 * 2. Eureka returns available instances (e.g., http://192.168.1.100:8082)
 * 3. Feign automatically load-balances between instances
 * 4. Requests are routed to endpoints like: GET /advertise/category
 * 
 * If olx-masterdata is unreachable, fallback is used (returns empty lists).
 */
@FeignClient(
	name = "olx-masterdata",
	fallback = MasterdataServiceFallback.class
)
public interface MasterdataServiceClient {

    /**
     * Returns all categories: { "categories": [{id, category}, ...] }
     * Maps to: GET /advertise/category in olx-masterdata
     */
    @GetMapping("/advertise/category")
    CategoryResponse getAllCategories();

    /**
     * Returns all statuses: { "statusList": [{id, status}, ...] }
     * Maps to: GET /advertise/status in olx-masterdata
     */
    @GetMapping("/advertise/status")
    StatusListResponse getAllStatuses();
}
