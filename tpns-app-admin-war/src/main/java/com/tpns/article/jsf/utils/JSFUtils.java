package com.tpns.article.jsf.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.tpns.core.errors.BusinessError;
import com.tpns.core.errors.BusinessException;

public class JSFUtils {

	public final static void outputBusinessExceptionToComponent(BusinessException businessException, FacesContext facesContext, String componentId) {
		for (BusinessError businessError : businessException.getBusinessErrors()) {
			facesContext.addMessage("form", new FacesMessage(FacesMessage.SEVERITY_ERROR, businessError.getMessage(), businessError.getMessage()));
		}
	}

	public final static String getMessageFromMessageBundle(FacesContext facesContext, String messageKey) {
		return facesContext.getApplication().getResourceBundle(facesContext, JSFConstants.MESSAGE_BUNDLE_VAR_NAME).getString(messageKey);
	}
}
