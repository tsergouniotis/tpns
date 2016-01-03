package com.tpns.article.validation.dto.validators;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpns.common.ws.rs.client.BasicTpnsAuthenticator;
import com.tpns.article.conf.ApplicationParameter;
import com.tpns.article.validation.dto.constraints.ExistingAuthor;
import com.tpns.utils.StringUtils;

public class ExistingAuthorValidator implements ConstraintValidator<ExistingAuthor, String> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExistingAuthorValidator.class.getPackage().getName());

	@Inject
	private Client client;

	@Inject
	@ApplicationParameter("user.service.uri")
	private String userServiceURI;

	@Override
	public void initialize(ExistingAuthor constraintAnnotation) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (StringUtils.hasText(value)) {

			int status = check(value);
			if (Status.OK.getStatusCode() != status) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addConstraintViolation();
				return false;
			}

		}
		return true;
	}

	private int check(String value) {
		try {
			LOGGER.info("Check if user exists");
			String uri = userServiceURI + "?username=" + value;
			LOGGER.debug("user service uri " + uri);
			WebTarget target = client.target(uri);
			final Response res = target.request(MediaType.APPLICATION_JSON).get();
			return res.getStatus();
		} catch (Exception e) {
			LOGGER.error("Could not validate author using author service ", e);
			return -1;
		}
	}

}
