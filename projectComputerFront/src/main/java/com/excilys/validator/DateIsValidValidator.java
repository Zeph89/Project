package com.excilys.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateIsValidValidator implements ConstraintValidator<DateIsValid, String> {

    @Override
    public void initialize(DateIsValid dateIsValid) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date fieldDate = null;

        if (value != null) {
            try {
                fieldDate = formatter.parse(value);
            } catch (ParseException e) {
                if (!value.equals("")) {
                    return false;
                }
            }
        }
        return true;
    }
}