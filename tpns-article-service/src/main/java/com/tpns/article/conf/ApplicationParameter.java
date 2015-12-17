package com.tpns.article.conf;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationParameter {
	/**
	 * Application parameter key
	 * 
	 * @return application parameter key
	 */
	@Nonbinding
	String key() default "";
}
