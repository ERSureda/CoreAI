package com.taxai.api.dispatch.application.port.in;

import com.taxai.api.dispatch.application.result.OfferResult;
import com.taxai.api.dispatch.infrastructure.adapter.in.web.dto.RejectOfferViaChannelHttpRequest;
import java.util.UUID;

public interface RejectOfferViaChannelUseCase { OfferResult execute(UUID driverId, RejectOfferViaChannelHttpRequest request); }
