package com.tpns.error;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BusinessException extends Exception {

	private static final long serialVersionUID = -3242183323713868970L;

	private List<BusinessError> errors = new ArrayList<>();

	private BusinessException(List<BusinessError> errors, String message) {
		super(message);
		this.errors.addAll(errors);
	}

	private BusinessException(List<BusinessError> errors, String message, Throwable throwable) {
		super(message, throwable);
		this.errors.addAll(errors);
	}

	public Iterator<BusinessError> getBusinessErrors() {
		return this.errors.iterator();
	}

	private static String getSingleMessageFromErrorList(List<BusinessError> errors) {
		StringBuilder sb = new StringBuilder();
		Iterator<BusinessError> it = errors.iterator();
		while (it.hasNext()) {
			sb.append(it.next().getMessage());
			if (it.hasNext()) {
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	public static BusinessException create(String message) {
		List<BusinessError> errors = new ArrayList<>();
		errors.add(BusinessError.create(message));
		return new BusinessException(errors, getSingleMessageFromErrorList(errors));
	}

	public static BusinessException create(String message, Throwable throwable) {
		List<BusinessError> errors = new ArrayList<>();
		errors.add(BusinessError.create(message));
		return new BusinessException(errors, getSingleMessageFromErrorList(errors));
	}

	public static BusinessException create(List<BusinessError> errors) {
		return new BusinessException(errors, getSingleMessageFromErrorList(errors));
	}

	public static BusinessException create(List<BusinessError> errors, Throwable throwable) {
		return new BusinessException(errors, getSingleMessageFromErrorList(errors));
	}

}
