package com.sushma.olxadvertise.client.helper;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sushma.olxadvertise.client.MasterdataServiceClient;

/**
 * Helper that wraps MasterdataServiceClient (Feign interface) and exposes
 * convenient lookup methods used by AdvertiseServiceImpl.
 *
 * Keeping this logic here means the Feign interface stays clean (one method
 * per remote endpoint) while the service layer never has to parse raw Maps.
 */
@Component
public class MasterdataClientHelper {

    @Autowired
    private MasterdataServiceClient masterdataServiceClient;

    /** Returns the category name for a given categoryId, or "Unknown". */
    @SuppressWarnings("unchecked")
    public String getCategoryName(int categoryId) {
        try {
            Map<String, Object> response = masterdataServiceClient.getAllCategories();
            List<Map<String, Object>> categories =
                    (List<Map<String, Object>>) response.get("categories");
            if (categories == null) return "Unknown";
            return categories.stream()
                    .filter(c -> ((Number) c.get("id")).intValue() == categoryId)
                    .map(c -> (String) c.get("category"))
                    .findFirst()
                    .orElse("Unknown");
        } catch (Exception e) {
            return "Unknown";
        }
    }

    /** Returns the category id for a given name (case-insensitive), or null. */
    @SuppressWarnings("unchecked")
    public Integer getCategoryId(String categoryName) {
        if (categoryName == null || categoryName.isBlank()) return null;
        try {
            Map<String, Object> response = masterdataServiceClient.getAllCategories();
            List<Map<String, Object>> categories =
                    (List<Map<String, Object>>) response.get("categories");
            if (categories == null) return null;
            return categories.stream()
                    .filter(c -> categoryName.equalsIgnoreCase((String) c.get("category")))
                    .map(c -> ((Number) c.get("id")).intValue())
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    /** Returns the status name for a given statusId, or "Unknown". */
    @SuppressWarnings("unchecked")
    public String getStatusName(int statusId) {
        try {
            Map<String, Object> response = masterdataServiceClient.getAllStatuses();
            List<Map<String, Object>> statusList =
                    (List<Map<String, Object>>) response.get("statusList");
            if (statusList == null) return "Unknown";
            return statusList.stream()
                    .filter(s -> ((Number) s.get("id")).intValue() == statusId)
                    .map(s -> (String) s.get("status"))
                    .findFirst()
                    .orElse("Unknown");
        } catch (Exception e) {
            return "Unknown";
        }
    }

    /** Returns the id of the OPEN status (defaults to 1 if unavailable). */
    @SuppressWarnings("unchecked")
    public int getOpenStatusId() {
        try {
            Map<String, Object> response = masterdataServiceClient.getAllStatuses();
            List<Map<String, Object>> statusList =
                    (List<Map<String, Object>>) response.get("statusList");
            if (statusList == null) return 1;
            return statusList.stream()
                    .filter(s -> "OPEN".equalsIgnoreCase((String) s.get("status")))
                    .map(s -> ((Number) s.get("id")).intValue())
                    .findFirst()
                    .orElse(1);
        } catch (Exception e) {
            return 1;
        }
    }
}
