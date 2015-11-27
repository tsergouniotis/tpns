package com.tpns.article.jsf.converters;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.tpns.article.dto.CategoryDTO;
import com.tpns.article.services.CategoryService;
import com.tpns.utils.StringUtils;

@FacesConverter("categoryConverter")
public class CategoryConverter implements Converter {

	@EJB
	private CategoryService categoryService;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (!StringUtils.hasText(value))
			return null;
		return categoryService.getByName(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (null == value)
			return null;
		return CategoryDTO.class.cast(value).getName();
	}

}
