package com.tpns.article.validation.dto.constraints;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.tpns.article.validation.dto.validators.ExistingAuthorValidator;

@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = ExistingAuthorValidator.class)
public @interface ExistingAuthor {

	String message() default "{com.tpns.article.validation.dto.constraints.ExistingAuthor.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
