package com.taxai.api.pricing.application.port.in;

import com.taxai.api.pricing.application.result.ListTariffsResult;
import com.taxai.api.pricing.infrastructure.adapter.in.web.dto.ListTariffsHttpRequest;

public interface ListTariffsUseCase { ListTariffsResult execute(ListTariffsHttpRequest request); }
