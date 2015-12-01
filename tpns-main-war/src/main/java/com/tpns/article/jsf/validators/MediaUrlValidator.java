package com.tpns.article.jsf.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.tpns.utils.StringUtils;

@FacesValidator("mediaUrlValidator")
public class MediaUrlValidator implements Validator {

	private static final String mediaUrlPattern = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

	private Pattern pattern;
	private Matcher matcher;

	public MediaUrlValidator() {
		pattern = Pattern.compile(mediaUrlPattern);
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (null == value || StringUtils.isEmptyString(value.toString()))
			return;
		matcher = pattern.matcher(value.toString());
		if (!matcher.matches()) {
			String errorMsg = context.getApplication().getResourceBundle(context, "messages").getString("validation.error.invalidurlformat");
			FacesMessage msg = new FacesMessage("Media url validation failed", errorMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}

}
