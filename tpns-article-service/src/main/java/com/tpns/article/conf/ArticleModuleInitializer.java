package com.tpns.article.conf;

import java.text.MessageFormat;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.inject.Named;

import com.tpns.article.repository.ApplicationParameterDAO;
import com.tpns.utils.StringUtils;

@Named
@ApplicationScoped
public class ArticleModuleInitializer {

	private static final String PARAM_MISSING = "No param founded for key {0}";

	private static final String INVALID_KEY = "The specified key {0} does not exist.";

	@Inject
	private ApplicationParameterDAO applicationParameterDAO;

	@Produces
	@ApplicationParameter
	public String injectConfiguration(final InjectionPoint ip) throws IllegalStateException {
		final ApplicationParameter param = ip.getAnnotated().getAnnotation(ApplicationParameter.class);
		if (StringUtils.isEmptyString(param.key())) {
			throw new IllegalStateException(MessageFormat.format(INVALID_KEY, new Object[] { param.key() }));
		}

		final String value = applicationParameterDAO.value(param.key());
		if (StringUtils.isEmptyString(value)) {
			throw new IllegalStateException(MessageFormat.format(PARAM_MISSING, new Object[] { param.key() }));
		}
		return value;
	}

}
