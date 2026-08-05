package com.taxai.api.dispatch.application.port.in;

import com.taxai.api.dispatch.application.result.OfferResult;
import java.util.UUID;

public interface RejectOfferUseCase { OfferResult execute(UUID offerId); }
