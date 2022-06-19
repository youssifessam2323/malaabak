package com.joework.Malaabak.validators;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Constraint(validatedBy = PasswordValidator.class)
@Target({FIELD, METHOD})
@Retention(RUNTIME)
public @interface Password {
    public String message() default "must contain uppercase letter, number, special character";
    
    //represents group of constraints     
        public Class<?>[] groups() default {};  
    //represents additional information about annotation  
        public Class<? extends Payload>[] payload() default {};  
}
