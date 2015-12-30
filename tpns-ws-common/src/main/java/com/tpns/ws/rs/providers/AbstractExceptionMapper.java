package com.tpns.ws.rs.providers;

import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.tpns.error.ErrorDTO;

public abstract class AbstractExceptionMapper<T extends Throwable> implements ExceptionMapper<T> {

	protected Response response(final Response.ResponseBuilder response, List<ErrorDTO> errors) {

		response.entity(new GenericEntity<List<ErrorDTO>>(errors, new GenericType<List<ErrorDTO>>() {
		}.getType()));

		return response.build();
	}

}
