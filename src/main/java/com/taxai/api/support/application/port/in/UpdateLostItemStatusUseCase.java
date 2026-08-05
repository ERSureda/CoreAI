package com.taxai.api.support.application.port.in;

import com.taxai.api.support.application.result.LostItemResult;
import com.taxai.api.support.infrastructure.adapter.in.web.dto.UpdateLostItemStatusHttpRequest;
import java.util.UUID;

public interface UpdateLostItemStatusUseCase { LostItemResult execute(UUID id, UpdateLostItemStatusHttpRequest request); }
