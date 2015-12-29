package com.tpns.error;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "error")
public class ErrorDTO {

	private String invalidValue;

	private String message;

	private String messageTemplate;

	private String path;

	/**
	 * jax-rs, jaxb constructor
	 */
	public ErrorDTO() {
		// TODO Auto-generated constructor stub
	}

	public ErrorDTO(String invalidValue, String message, String messageTemplate, String path) {
		this.invalidValue = invalidValue;
		this.message = message;
		this.messageTemplate = messageTemplate;
		this.path = path;
	}

	public ErrorDTO(String message) {
		this.message = message;
	}

	@XmlElement(name = "invalidValue")
	public String getInvalidValue() {
		return invalidValue;
	}

	@XmlElement(name = "message")
	public String getMessage() {
		return message;
	}

	@XmlElement(name = "messageTemplate")
	public String getMessageTemplate() {
		return messageTemplate;
	}

	@XmlElement(name = "path")
	public String getPath() {
		return path;
	}

}
