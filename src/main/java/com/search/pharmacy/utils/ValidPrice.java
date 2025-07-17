package com.search.pharmacy.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PriceValidator.class)
@Documented
public @interface ValidPrice {

    String message() default "Le nouveau prix doit être inférieur à l'ancien prix";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String oldPriceField() default "oldPrice";
    String newPriceField() default "newPrice";
}
