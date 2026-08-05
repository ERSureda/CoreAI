package com.taxai.api.support.application.port.in;

import com.taxai.api.support.application.result.LostItemResult;
import java.util.UUID;

public interface GetLostItemUseCase { LostItemResult execute(UUID id); }
