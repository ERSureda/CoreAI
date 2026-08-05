package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.result.ListDriversResult;
import com.taxai.api.fleet.infrastructure.adapter.in.web.dto.ListDriversHttpRequest;

public interface ListDriversUseCase {
    ListDriversResult execute(ListDriversHttpRequest request);
}
