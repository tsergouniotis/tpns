package com.tpns.article.validation.internal;

import java.util.Locale;

import javax.validation.MessageInterpolator;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;

/**
 *
 * Delegates to a MessageInterpolator implementation but enforces a given
 * Locale.
 *
 */
public class LocaleSpecificMessageInterpolator implements MessageInterpolator {

	private final MessageInterpolator defaultInterpolator;

	public LocaleSpecificMessageInterpolator() {
		this.defaultInterpolator = new ResourceBundleMessageInterpolator();
	}

	/**
	 *
	 * Enforces the locale passed to the interpolator.
	 *
	 */
	@Override
	public String interpolate(final String message, final Context context) {
		return interpolate(message, context, LocaleThreadLocal.get());
	}

	// no real use, implemented for completeness
	@Override
	public String interpolate(final String message, final Context context, final Locale locale) {
		return defaultInterpolator.interpolate(message, context, locale);
	}

}
