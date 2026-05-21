package com.sushma.olxadvertise.client.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sushma.olxadvertise.client.MasterdataServiceClient;
import com.sushma.olxadvertise.dto.masterdataclient.CategoryResponse;
import com.sushma.olxadvertise.dto.masterdataclient.StatusListResponse;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class MasterdataClientHelper {

    @Autowired
    private MasterdataServiceClient masterdataServiceClient;

    /** Returns the category name for a given categoryId, or "Unknown". */
    public String getCategoryName(int categoryId) {
        try {
            CategoryResponse response = masterdataServiceClient.getAllCategories();
            log.info("getCategoryName - raw response: {}", response);
            if (response == null || response.getCategories() == null) {
                log.warn("getCategoryName - response or categories list is null for categoryId={}", categoryId);
                return "Unknown";
            }
            return response.getCategories().stream()
                    .filter(c -> c.getId() == categoryId)
                    .map(c -> c.getCategory())
                    .findFirst()
                    .orElseGet(() -> {
                        log.warn("getCategoryName - no match for categoryId={}, available: {}", categoryId, response.getCategories());
                        return "Unknown";
                    });
        } catch (Exception e) {
            log.error("getCategoryName failed for categoryId={}: {}", categoryId, e.getMessage(), e);
            return "Unknown";
        }
    }

    /** Returns the category id for a given name (case-insensitive), or null. */
    public Integer getCategoryId(String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) return null;
        try {
            CategoryResponse response = masterdataServiceClient.getAllCategories();
            log.info("getCategoryId - raw response: {}", response);
            if (response == null || response.getCategories() == null) {
                log.warn("getCategoryId - response or categories list is null for categoryName={}", categoryName);
                return null;
            }
            return response.getCategories().stream()
                    .filter(c -> categoryName.equalsIgnoreCase(c.getCategory()))
                    .map(c -> c.getId())
                    .findFirst()
                    .orElseGet(() -> {
                        log.warn("getCategoryId - no match for categoryName={}", categoryName);
                        return null;
                    });
        } catch (Exception e) {
            log.error("getCategoryId failed for categoryName='{}': {}", categoryName, e.getMessage(), e);
            return null;
        }
    }

    /** Returns the status name for a given statusId, or "Unknown". */
    public String getStatusName(int statusId) {
        try {
            StatusListResponse response = masterdataServiceClient.getAllStatuses();
            log.info("getStatusName - raw response: {}", response);
            if (response == null || response.getStatusList() == null) {
                log.warn("getStatusName - response or statusList is null for statusId={}", statusId);
                return "Unknown";
            }
            return response.getStatusList().stream()
                    .filter(s -> s.getId() == statusId)
                    .map(s -> s.getStatus())
                    .findFirst()
                    .orElseGet(() -> {
                        log.warn("getStatusName - no match for statusId={}, available: {}", statusId, response.getStatusList());
                        return "Unknown";
                    });
        } catch (Exception e) {
            log.error("getStatusName failed for statusId={}: {}", statusId, e.getMessage(), e);
            return "Unknown";
        }
    }

    /** Returns the id of the OPEN status (defaults to 1 if unavailable). */
    public int getOpenStatusId() {
        try {
            StatusListResponse response = masterdataServiceClient.getAllStatuses();
            log.info("getOpenStatusId - raw response: {}", response);
            if (response == null || response.getStatusList() == null) {
                log.warn("getOpenStatusId - response or statusList is null, defaulting to 1");
                return 1;
            }
            return response.getStatusList().stream()
                    .filter(s -> "OPEN".equalsIgnoreCase(s.getStatus()))
                    .map(s -> s.getId())
                    .findFirst()
                    .orElseGet(() -> {
                        log.warn("getOpenStatusId - OPEN status not found, defaulting to 1");
                        return 1;
                    });
        } catch (Exception e) {
            log.error("getOpenStatusId failed: {}", e.getMessage(), e);
            return 1;
        }
    }
}
