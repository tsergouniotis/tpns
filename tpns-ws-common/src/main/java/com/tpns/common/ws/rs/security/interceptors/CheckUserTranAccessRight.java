package com.tpns.common.ws.rs.security.interceptors;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;

@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface CheckUserTranAccessRight {

	/**
	 * This flag in the annotation, indicates if the interceptor when invoked
	 * for a specific call will will need to check if the loggedin user has the
	 * 'access' right to initiate from scratch a transaction. Usually the flag
	 * is set to true in the resource methods that are called 'input' since
	 * these are the entry points business wise for a business transaction.
	 * 
	 * @return boolean, default is false.
	 */
	@Nonbinding
	boolean value() default false;
}
