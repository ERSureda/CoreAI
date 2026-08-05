package com.taxai.api.dispatch.application.port.in;

import com.taxai.api.dispatch.application.result.ListOffersResult;
import java.util.UUID;

public interface ListPendingDriverOffersUseCase { ListOffersResult execute(UUID driverId); }
