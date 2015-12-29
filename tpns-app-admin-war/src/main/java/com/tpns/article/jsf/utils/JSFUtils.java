package com.tpns.article.jsf.utils;

import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.tpns.error.BusinessError;
import com.tpns.error.BusinessException;

public class JSFUtils {

	public final static void outputBusinessExceptionToComponent(BusinessException businessException, FacesContext facesContext, String componentId) {
		Iterator<BusinessError> itr = businessException.getBusinessErrors();
		while (itr.hasNext()) {
			BusinessError businessError = itr.next();
			facesContext.addMessage("form", new FacesMessage(FacesMessage.SEVERITY_ERROR, businessError.getMessage(), businessError.getMessage()));
		}
	}

	public final static String getMessageFromMessageBundle(FacesContext facesContext, String messageKey) {
		return facesContext.getApplication().getResourceBundle(facesContext, JSFConstants.MESSAGE_BUNDLE_VAR_NAME).getString(messageKey);
	}
}
