package com.taxai.api.pricing.application.port.in;

import com.taxai.api.pricing.application.result.ListReceiptsResult;
import com.taxai.api.pricing.infrastructure.adapter.in.web.dto.ListReceiptsHttpRequest;

public interface ListReceiptsUseCase { ListReceiptsResult execute(ListReceiptsHttpRequest request); }
