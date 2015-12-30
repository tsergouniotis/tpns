package com.tpns.article.interceptors;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.tpns.common.domain.errors.BusinessError;
import com.tpns.common.domain.errors.BusinessErrorCode;
import com.tpns.common.domain.errors.BusinessException;

@Interceptor
public class ArticleManagerInterceptor {

	@AroundInvoke
	public Object proceed(final InvocationContext ctx) throws Exception {

		try {

			return ctx.proceed();

		} catch (final BusinessException e) {

			throw e;

		} catch (final ConstraintViolationException e) {
			throw newBusinessException(e.getConstraintViolations());
		} catch (final Exception e) {
			throw BusinessException.create(e.getMessage());
		}

	}

	private static BusinessException newBusinessException(final Set<ConstraintViolation<?>> constraintViolations) {
		final List<BusinessError> errors = new ArrayList<>();
		for (final ConstraintViolation<?> constraintViolation : constraintViolations) {
			errors.add(BusinessError.create(constraintViolation.getMessage(), BusinessErrorCode.VALIDATION));
		}
		return BusinessException.create(errors);
	}

}
