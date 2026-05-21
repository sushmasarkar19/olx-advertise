package com.sushma.olxadvertise.client.fallback;

import java.util.Collections;

import org.springframework.stereotype.Component;

import com.sushma.olxadvertise.client.MasterdataServiceClient;
import com.sushma.olxadvertise.dto.masterdataclient.CategoryResponse;
import com.sushma.olxadvertise.dto.masterdataclient.StatusListResponse;

/**
 * Feign fallback for MasterdataServiceClient.
 * Returns safe EMPTY (not null) responses when olx-masterdata is unreachable,
 * preventing NullPointerExceptions in the helper layer.
 */
@Component
public class MasterdataServiceFallback implements MasterdataServiceClient {

    @Override
    public CategoryResponse getAllCategories() {
        return new CategoryResponse(Collections.emptyList());
    }

    @Override
    public StatusListResponse getAllStatuses() {
        return new StatusListResponse(Collections.emptyList());
    }
}
