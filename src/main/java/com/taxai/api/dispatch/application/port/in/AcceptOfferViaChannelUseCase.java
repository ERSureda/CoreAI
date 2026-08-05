package com.taxai.api.dispatch.application.port.in;

import com.taxai.api.dispatch.application.result.OfferResult;
import com.taxai.api.dispatch.infrastructure.adapter.in.web.dto.AcceptOfferViaChannelHttpRequest;
import java.util.UUID;

public interface AcceptOfferViaChannelUseCase { OfferResult execute(UUID driverId, AcceptOfferViaChannelHttpRequest request); }
