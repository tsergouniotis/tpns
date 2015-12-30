package com.tpns.ws.rs.providers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;
import javax.validation.Path;
import javax.validation.ValidationException;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Variant;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpns.error.ErrorDTO;

/**
 * {@link ExceptionMapper} for {@link ValidationException}.
 * <p>
 * Send a list of {@link ErrorDTO} instances in {@link Response} in addition to
 * HTTP 400/500 status code. Supported media types are: {@code application/json}
 * / {@code application/xml} (if appropriate provider is registered on server).
 * </p>
 * 
 * @author sergouniotis based on <a href=
 *         "https://jaxenter.com/integrating-bean-validation-with-jax-rs-2-106887.html">
 *         https://jaxenter.com/integrating-bean-validation-with-jax-rs-2-106887
 *         .html</a>
 */
@Provider
public class ValidationExceptionMapper extends AbstractExceptionMapper<ValidationException> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidationExceptionMapper.class.getName());

	@Context
	private Configuration config;

	@Context
	private Request request;

	@Override
	public Response toResponse(final ValidationException exception) {
		if (exception instanceof ConstraintViolationException) {
			LOGGER.trace("Following ConstraintViolations has been encountered.", exception);
			final ConstraintViolationException e = ConstraintViolationException.class.cast(exception);
			final Response.ResponseBuilder response = Response.status(getStatus(e));

			// Entity
			final List<Variant> variants = Variant.mediaTypes(MediaType.APPLICATION_XML_TYPE, MediaType.APPLICATION_JSON_TYPE).build();
			final Variant variant = request.selectVariant(variants);
			if (variant != null) {
				response.type(variant.getMediaType());
			}
			List<ErrorDTO> errors = getEntity(e.getConstraintViolations());

			return response(response, errors);
		} else {
			LOGGER.warn("Unexpected Bean Validation problem.", exception);

			return Response.serverError().entity(exception.getMessage()).build();
		}
	}

	private List<ErrorDTO> getEntity(final Set<ConstraintViolation<?>> violations) {
		final List<ErrorDTO> errors = new ArrayList<ErrorDTO>();

		for (final ConstraintViolation<?> violation : violations) {
			errors.add(new ErrorDTO(getInvalidValue(violation.getInvalidValue()), violation.getMessage(), violation.getMessageTemplate(), getPath(violation)));
		}

		return errors;
	}

	private String getInvalidValue(final Object invalidValue) {
		if (invalidValue == null) {
			return null;
		}

		if (invalidValue.getClass().isArray()) {
			return Arrays.toString((Object[]) invalidValue);
		}

		return invalidValue.toString();
	}

	private Response.Status getStatus(final ConstraintViolationException exception) {
		return getResponseStatus(exception.getConstraintViolations());
	}

	private Response.Status getResponseStatus(final Set<ConstraintViolation<?>> constraintViolations) {
		final Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();

		if (iterator.hasNext()) {
			return getResponseStatus(iterator.next());
		} else {
			return Response.Status.BAD_REQUEST;
		}
	}

	private Response.Status getResponseStatus(final ConstraintViolation<?> constraintViolation) {
		for (final Path.Node node : constraintViolation.getPropertyPath()) {
			final ElementKind kind = node.getKind();

			if (ElementKind.RETURN_VALUE.equals(kind)) {
				return Response.Status.INTERNAL_SERVER_ERROR;
			}
		}

		return Response.Status.BAD_REQUEST;
	}

	private String getPath(final ConstraintViolation<?> violation) {
		final String leafBeanName = violation.getLeafBean().getClass().getSimpleName();
		final String leafBeanCleanName = (leafBeanName.contains("$")) ? leafBeanName.substring(0, leafBeanName.indexOf("$")) : leafBeanName;
		final String propertyPath = violation.getPropertyPath().toString();

		return leafBeanCleanName + (!"".equals(propertyPath) ? '.' + propertyPath : "");
	}
}