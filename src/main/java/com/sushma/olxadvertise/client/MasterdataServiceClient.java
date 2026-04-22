package com.sushma.olxadvertise.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.sushma.olxadvertise.client.fallback.MasterdataServiceFallback;

/**
 * Feign client for olx-masterdata service.
 * Base URL is resolved from application.properties via 'url' attribute.
 * Fallback handles the case when olx-masterdata is unreachable.
 */
@FeignClient(name = "olx-masterdata", url = "${olx.masterdata.service.url}", fallback = MasterdataServiceFallback.class)
public interface MasterdataServiceClient {

    /**
     * Returns all categories: { "categories": [{id, category}, ...] }
     * Maps to: GET /advertise/category in olx-masterdata
     */
    @GetMapping("/advertise/category")
    Map<String, Object> getAllCategories();

    /**
     * Returns all statuses: { "statusList": [{id, status}, ...] }
     * Maps to: GET /advertise/status in olx-masterdata
     */
    @GetMapping("/advertise/status")
    Map<String, Object> getAllStatuses();
}
