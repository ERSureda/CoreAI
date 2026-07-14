package com.taxai.api.shared.domain.valueobject;

public record CountryCode(String value) {

    public CountryCode {
        if (value == null || value.length() != 2 || !value.chars().allMatch(Character::isLetter)) {
            throw new IllegalArgumentException("COUNTRY_CODE_INVALID");
        }
        value = value.toUpperCase();
    }

    public static CountryCode of(String value) {
        return new CountryCode(value);
    }
}
