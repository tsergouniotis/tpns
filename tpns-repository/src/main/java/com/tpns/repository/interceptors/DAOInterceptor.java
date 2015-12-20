package com.tpns.repository.interceptors;

import java.time.Duration;
import java.time.Instant;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Interceptor
@MonitorExecution
public class DAOInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(DAOInterceptor.class);

	@AroundInvoke
	public Object intercept(InvocationContext context) throws Exception {

		Instant start = Instant.now();

		Object result = context.proceed();

		Instant end = Instant.now();

		LOGGER.debug(context.getTarget().getClass().getSimpleName() + "." + context.getMethod().getName() + "\t" + Duration.between(start, end).toString());

		return result;

	}

}
