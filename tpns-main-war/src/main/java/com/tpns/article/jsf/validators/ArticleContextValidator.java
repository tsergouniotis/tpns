package com.tpns.article.jsf.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.tpns.article.jsf.utils.JSFUtils;

@FacesValidator("articleContextValidator")
public class ArticleContextValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (value.toString().length() < 100) {
			String errorMsg = JSFUtils.getMessageFromMessageBundle(context, "validation.error.article.invalidcontentlength");
			FacesMessage msg = new FacesMessage("Media url validation failed", errorMsg);
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}

}
