package com.tpns.article.validation.domain.constraints;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.tpns.article.validation.domain.validators.ArticleValidator;

@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = ArticleValidator.class)
public @interface ValidArticle {

	String message() default "{com.tpns.article.validation.domain.constraints.ValidArticle.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
