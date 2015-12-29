package com.tpns.article.interceptors;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.tpns.core.errors.BusinessError;
import com.tpns.core.errors.BusinessErrorCode;
import com.tpns.core.errors.BusinessException;

@Interceptor
public class ArticleManagerInterceptor {

	@AroundInvoke
	public Object proceed(InvocationContext ctx) throws Exception {

		try {

			return ctx.proceed();

		} catch (BusinessException e) {

			throw e;

		} catch (ConstraintViolationException e) {
			throw newBusinessException(e.getConstraintViolations());
		} catch (Exception e) {
			throw BusinessException.create(e.getMessage());
		}

	}

	private static BusinessException newBusinessException(Set<ConstraintViolation<?>> constraintViolations) {
		List<BusinessError> errors = new ArrayList<>();
		for (ConstraintViolation<?> constraintViolation : constraintViolations) {
			errors.add(BusinessError.create(constraintViolation.getMessage(), BusinessErrorCode.VALIDATION));
		}
		return BusinessException.create(errors);
	}

}
