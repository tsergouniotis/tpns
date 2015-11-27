package com.tpns.article.services.interceptors;

import java.util.concurrent.Future;

import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpns.article.dto.ArticleDTO;
import com.tpns.article.services.ArticleDispatcher;

@Dispatch
@Interceptor
public class DispatcherInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherInterceptor.class.getPackage().getName());

	@EJB
	private ArticleDispatcher articleDispatcher;

	@AroundInvoke
	public Object sendConfirmMessage(InvocationContext ctx) throws Exception {

		pre(ctx);

		return ctx.proceed();

	}

	private void pre(InvocationContext ctx) {
		Object[] parameters = ctx.getParameters();

		ArticleDTO article = ArticleDTO.class.cast(parameters[0]);

		Future<Boolean> dispatch = articleDispatcher.dispatch(article);

		if (null == dispatch) {
			LOGGER.error("Something went wrong during dispatching.The session context was cancelled");
		}
	}

}
