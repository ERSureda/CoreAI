package com.taxai.api.support.application.port.in;

import com.taxai.api.support.application.result.ListLostItemsResult;
import com.taxai.api.support.infrastructure.adapter.in.web.dto.ListLostItemsHttpRequest;

public interface ListLostItemsUseCase { ListLostItemsResult execute(ListLostItemsHttpRequest request); }
