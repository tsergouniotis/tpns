package com.tpns.article.interceptors;

import java.util.concurrent.Future;

import javax.ejb.EJB;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpns.article.domain.Article;
import com.tpns.article.managers.ArticleDispatcher;
import com.tpns.utils.Assert;

@Dispatch
@Interceptor
public class DispatcherInterceptor {

	private static final int SINGLE_ELEMENT_ARRAY_SIZE = 1;

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

		Assert.isTrue(SINGLE_ELEMENT_ARRAY_SIZE == parameters.length);

		Article article = Article.class.cast(parameters[0]);

		Future<Boolean> dispatch = articleDispatcher.dispatch(article);

		if (null == dispatch) {
			LOGGER.error("Something went wrong during dispatching.The session context was cancelled");
		}
	}

}
