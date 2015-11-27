package com.tpns.error;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class BusinessException extends Exception{

	private List<BusinessError> errors = new ArrayList<>();	
	
	private BusinessException(List<BusinessError> errors, String message) {
        super(message);
        this.errors.addAll(errors);
    }

	private BusinessException(List<BusinessError> errors, String message, Throwable throwable) {
        super(message, throwable);
        this.errors.addAll(errors);
    }
    
    private static String getSingleMessageFromErrorList(List<BusinessError> errors){
    	return StringUtils.join(errors, "\n");
    }

    public static BusinessException create(String message){
    	List<BusinessError> errors = new ArrayList<>();
    	errors.add(BusinessError.create(message));
    	return new BusinessException(errors, getSingleMessageFromErrorList(errors));
    }
    
    public static BusinessException create(String message, Throwable throwable){
    	List<BusinessError> errors = new ArrayList<>();
    	errors.add(BusinessError.create(message));
    	return new BusinessException(errors, getSingleMessageFromErrorList(errors));
    }
    
    public static BusinessException create(List<BusinessError> errors){
    	return new BusinessException(errors, getSingleMessageFromErrorList(errors));
    }
    
    public static BusinessException create(List<BusinessError> errors, Throwable throwable){
    	return new BusinessException(errors, getSingleMessageFromErrorList(errors));
    }
    
}
