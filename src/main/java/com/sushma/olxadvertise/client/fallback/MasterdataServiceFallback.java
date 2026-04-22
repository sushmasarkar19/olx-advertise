package com.sushma.olxadvertise.client.fallback;

import java.util.Collections;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.sushma.olxadvertise.client.MasterdataServiceClient;

/**
 * Feign fallback for MasterdataServiceClient.
 * Returns safe empty responses when olx-masterdata is unreachable,
 * so that advertise operations can still proceed with degraded data.
 */
@Component
public class MasterdataServiceFallback implements MasterdataServiceClient {

    @Override
    public Map<String, Object> getAllCategories() {
        return Collections.singletonMap("categories", Collections.emptyList());
    }

    @Override
    public Map<String, Object> getAllStatuses() {
        return Collections.singletonMap("statusList", Collections.emptyList());
    }
}
