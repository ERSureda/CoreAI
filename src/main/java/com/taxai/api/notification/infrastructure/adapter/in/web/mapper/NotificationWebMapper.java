package com.taxai.api.notification.infrastructure.adapter.in.web.mapper;

import com.taxai.api.notification.application.command.*;
import com.taxai.api.notification.infrastructure.adapter.in.web.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface NotificationWebMapper {

    CreateTemplateCommand toCreateTemplateCommand(CreateTemplateHttpRequest request);

    ListTemplatesCommand toListTemplatesCommand(ListTemplatesHttpRequest request);

    @Mapping(target = "id", source = "id")
    UpdateTemplateCommand toUpdateTemplateCommand(UUID id, UpdateTemplateHttpRequest request);

    GetPreferencesCommand toGetPreferencesCommand(GetPreferencesHttpRequest request);

    UpdatePreferencesCommand toUpdatePreferencesCommand(UpdatePreferencesHttpRequest request);
}
