package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.result.ListOperatorsResult;
import com.taxai.api.fleet.infrastructure.adapter.in.web.dto.ListOperatorsHttpRequest;

public interface ListOperatorsUseCase {
    ListOperatorsResult execute(ListOperatorsHttpRequest request);
}
