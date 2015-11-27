package com.tpns.article.interceptors;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.tpns.error.BusinessError;
import com.tpns.error.BusinessErrorCode;
import com.tpns.error.BusinessException;
import com.tpns.utils.CollectionUtils;

@ValidateParams
@Interceptor
public class ValidateParamsInterceptor {

	@Inject
	private Validator validator;

	@AroundInvoke
	public Object sendConfirmMessage(InvocationContext ctx) throws Exception {

		Object[] parameters = ctx.getParameters();
		for (Object object : parameters) {
			Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
			if (CollectionUtils.isNonEmpty(constraintViolations)) {
				throw newBusinessException(constraintViolations);
			}
		}

		return ctx.proceed();

	}

	private static <T> BusinessException newBusinessException(Set<ConstraintViolation<T>> constraintViolations) {
		List<BusinessError> errors = new ArrayList<>();
		for (ConstraintViolation<?> constraintViolation : constraintViolations) {
			errors.add(BusinessError.create(constraintViolation.getMessage(), BusinessErrorCode.VALIDATION));
		}
		return BusinessException.create(errors);
	}

}
