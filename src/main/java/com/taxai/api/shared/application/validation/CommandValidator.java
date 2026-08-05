package com.taxai.api.shared.application.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommandValidator {

    private final List<ValidationError> errors = new ArrayList<>();

    public static CommandValidator start() {
        return new CommandValidator();
    }

    public CommandValidator rejectIfBlank(String value, String code, String message) {
        if (value == null || value.trim().isEmpty()) {
            errors.add(new ValidationError(code, message));
        }
        return this;
    }

    public CommandValidator rejectIfNull(Object value, String code, String message) {
        if (value == null) {
            errors.add(new ValidationError(code, message));
        }
        return this;
    }

    public CommandValidator rejectIfInvalidUuid(String value, String code, String message) {
        if (value != null && !value.trim().isEmpty()) {
            try {
                UUID.fromString(value.trim());
            } catch (IllegalArgumentException e) {
                errors.add(new ValidationError(code, message));
            }
        }
        return this;
    }

    public CommandValidator rejectIf(boolean condition, String code, String message) {
        if (condition) {
            errors.add(new ValidationError(code, message));
        }
        return this;
    }

    public void validate(String commandName) {
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException("Validation failed for " + commandName + ": " + errors);
        }
    }

    public record ValidationError(String code, String message) {}
}
