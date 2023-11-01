package com.example.Library.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Calendar;
import java.util.Date;

public class AgeValidator implements ConstraintValidator<AgeOver12, Date> {

    @Override
    public void initialize(AgeOver12 constraintAnnotation) {
    }

    @Override
    public boolean isValid(Date dateOfBirth, ConstraintValidatorContext context) {
        if (dateOfBirth == null) {
            return false;
        }

        Calendar dob = Calendar.getInstance();
        dob.setTime(dateOfBirth);
        Calendar now = Calendar.getInstance();

        int age = now.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (dob.get(Calendar.MONTH) > now.get(Calendar.MONTH) ||
                (dob.get(Calendar.MONTH) == now.get(Calendar.MONTH) &&
                        dob.get(Calendar.DAY_OF_MONTH) > now.get(Calendar.DAY_OF_MONTH))) {
            age--;
        }

        return age >= 12;
    }
}
