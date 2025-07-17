package com.search.pharmacy.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.math.BigDecimal;

public class PriceValidator implements ConstraintValidator<ValidPrice, Object> {

    private String oldPriceField;
    private String newPriceField;

    @Override
    public void initialize(ValidPrice constraintAnnotation) {
        this.oldPriceField = constraintAnnotation.oldPriceField();
        this.newPriceField = constraintAnnotation.newPriceField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Laissez @NotNull gérer la nullité
        }

        try {
            Field oldPriceFieldObj = value.getClass().getDeclaredField(oldPriceField);
            Field newPriceFieldObj = value.getClass().getDeclaredField(newPriceField);

            oldPriceFieldObj.setAccessible(true);
            newPriceFieldObj.setAccessible(true);

            Double oldPrice = (Double) oldPriceFieldObj.get(value);
            Double newPrice = (Double) newPriceFieldObj.get(value);

            if (oldPrice == null || newPrice == null) {
                return true;
            }

            boolean isValid = newPrice.compareTo(oldPrice) < 0;

            if (!isValid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                                "Le nouveau prix (" + newPrice + ") doit être inférieur à l'ancien prix (" + oldPrice + ")")
                        .addPropertyNode(newPriceField)
                        .addConstraintViolation();
            }

            return isValid;

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Erreur lors de la validation des prix", e);
        }
    }
}
