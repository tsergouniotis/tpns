package com.tpns.common.ws.rs.security.interceptors;

import java.security.Principal;
import java.util.Map;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@CheckUserTranAccessRight
public class CheckUserTranAccessRightInterceptor {

	@Inject
	private Principal userPrincipal;

	@AroundInvoke
	public Object checkAccess(InvocationContext ctx) throws Exception {

		Map<String, Object> contextData = ctx.getContextData();

		return ctx.proceed();

	}

}
