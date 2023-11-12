package com.example.Library.util.customAnnotationsValidators;

import com.example.Library.util.customAnnotations.YearFormat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class YearFormatValidator implements ConstraintValidator<YearFormat, String> {
    @Override
    public void initialize(YearFormat constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null values are considered valid
        }

        // Regular expression to check if the value is a four-digit year("2004")
        return value.matches("^\\d{4}$");
    }
}