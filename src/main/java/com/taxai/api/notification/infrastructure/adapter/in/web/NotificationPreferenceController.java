package com.taxai.api.notification.infrastructure.adapter.in.web;

import com.taxai.api.notification.application.port.in.GetPreferencesUseCase;
import com.taxai.api.notification.application.port.in.UpdatePreferencesUseCase;
import com.taxai.api.notification.application.result.ListPreferencesResult;
import com.taxai.api.notification.application.result.PreferenceResult;
import com.taxai.api.notification.infrastructure.adapter.in.web.dto.GetPreferencesHttpRequest;
import com.taxai.api.notification.infrastructure.adapter.in.web.dto.UpdatePreferencesHttpRequest;
import com.taxai.api.notification.infrastructure.adapter.in.web.mapper.NotificationWebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/notifications/preferences")
@Tag(name = "Notification Preference Management", description = "API for user notification channel preferences and quiet hours.")
public class NotificationPreferenceController {

    private final NotificationWebMapper mapper;

    private final GetPreferencesUseCase getPreferencesUseCase;
    private final UpdatePreferencesUseCase updatePreferencesUseCase;

    @GetMapping
    @Operation(
            summary = "Get notification preferences",
            description = "Retrieves channel preferences for a given owner type and ID."
    )
    public ResponseEntity<ListPreferencesResult> getPreferences(@Valid GetPreferencesHttpRequest request) {
        return ResponseEntity
                .status(200)
                .body(getPreferencesUseCase.execute(request));
    }

    @PutMapping
    @Operation(
            summary = "Update notification preferences",
            description = "Creates or updates notification channel settings and quiet hours for an owner."
    )
    public ResponseEntity<PreferenceResult> updatePreferences(@Valid @RequestBody UpdatePreferencesHttpRequest request) {
        return ResponseEntity
                .status(200)
                .body(updatePreferencesUseCase.execute(request));
    }
}
