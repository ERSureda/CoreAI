package com.taxai.api.support.application.port.in;

import com.taxai.api.support.application.result.LostItemResult;
import com.taxai.api.support.infrastructure.adapter.in.web.dto.CreateLostItemHttpRequest;

public interface CreateLostItemUseCase { LostItemResult execute(CreateLostItemHttpRequest request); }
