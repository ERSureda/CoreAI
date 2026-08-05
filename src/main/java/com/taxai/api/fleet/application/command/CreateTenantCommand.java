package com.taxai.api.fleet.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;

public record CreateTenantCommand(
        String name,
        String taxId,
        String dataRegion,
        String defaultLanguage
) {
    public CreateTenantCommand {
        CommandValidator.start()
                .rejectIfBlank(name, "NAME_REQUIRED", "Name is required.")
                .rejectIfBlank(taxId, "TAX_ID_REQUIRED", "Tax ID is required.")
                .rejectIfBlank(defaultLanguage, "DEFAULT_LANGUAGE_REQUIRED", "Default language is required.")
                .validate(CreateTenantCommand.class.getSimpleName());
    }
}
