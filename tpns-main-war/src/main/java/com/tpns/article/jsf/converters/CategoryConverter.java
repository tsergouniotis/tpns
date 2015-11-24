package com.tpns.article.jsf.converters;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.tpns.article.domain.Category;
import com.tpns.article.services.CategoryService;

@FacesConverter("com.tpns.article.CategoryConverter")
public class CategoryConverter implements Converter {

	@EJB
	private CategoryService categoryService;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return categoryService.getByName(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		Category category = (Category) value;
		return category.getName();
	}

}
