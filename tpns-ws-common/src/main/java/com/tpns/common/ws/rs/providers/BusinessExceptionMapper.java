package com.tpns.common.ws.rs.providers;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import com.tpns.common.domain.errors.BusinessError;
import com.tpns.common.domain.errors.BusinessException;
import com.tpns.error.ErrorDTO;

@Provider
public class BusinessExceptionMapper extends AbstractExceptionMapper<BusinessException> {

	@Override
	public Response toResponse(BusinessException exception) {

		List<ErrorDTO> errors = toErrors(exception);

		return response(Response.status(Status.BAD_REQUEST), errors);
	}

	private List<ErrorDTO> toErrors(BusinessException exception) {

		List<ErrorDTO> result = new ArrayList<>();

		List<BusinessError> businessErrors = exception.getBusinessErrors();
		for (BusinessError businessError : businessErrors) {
			result.add(new ErrorDTO(businessError.getMessage()));
		}
		return result;
	}

}
