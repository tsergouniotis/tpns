package com.tpns.article.converters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import com.tpns.article.domain.Article;
import com.tpns.article.domain.MediaResource;
import com.tpns.article.domain.MediaResourceType;
import com.tpns.article.dto.ArticleDTO;
import com.tpns.utils.CollectionUtils;

@SessionScoped
public class ArticleConverter implements Serializable {

	@Inject
	private CategoryConverter categoryConverter;

	public ArticleDTO convert(Article article) {
		ArticleDTO dto = new ArticleDTO();
		dto.setId(article.getId());
		dto.setSubject(article.getSubject());
		dto.setShortDescription(article.getShortDescription());
		dto.setContent(article.getContent());
		dto.setCategory(categoryConverter.convert(article.getCategory()));
		dto.setCreatedAt(article.getCreatedAt());
		dto.setUpdatedAt(article.getUpdatedAt());
		dto.setPostedAt(article.getPostedAt());

		List<String> imageUrls = new ArrayList<>();

		List<String> videoUrls = new ArrayList<>();

		List<String> audioUrls = new ArrayList<>();

		for (MediaResource mediaResource : article.getResources()) {
			switch (mediaResource.getType()) {
			case IMAGE:
				imageUrls.add(mediaResource.getUrl());
			case VIDEO:
				videoUrls.add(mediaResource.getUrl());
			case AUDIO:
				audioUrls.add(mediaResource.getUrl());
			}
		}

		dto.setImageUrls(imageUrls);

		dto.setVideoUrls(videoUrls);

		dto.setAudioUrls(audioUrls);

		return dto;
	}

	public Article convert(ArticleDTO dto) {

		Article article = new Article();
		article.setSubject(dto.getSubject());
		article.setShortDescription(dto.getShortDescription());
		article.setContent(dto.getContent());

		article.setCategory(categoryConverter.convert(dto.getCategory()));

		article.setCreatedAt(dto.getCreatedAt());
		article.setUpdatedAt(dto.getUpdatedAt());
		article.setPostedAt(dto.getPostedAt());

		List<MediaResource> articleMedia = new ArrayList<MediaResource>();
		for (String imageUrl : dto.getImageUrls()) {
			articleMedia.add(new MediaResource(MediaResourceType.IMAGE, imageUrl));
		}
		for (String videoUrl : dto.getVideoUrls()) {
			articleMedia.add(new MediaResource(MediaResourceType.VIDEO, videoUrl));
		}
		for (String audioUrl : dto.getAudioUrls()) {
			articleMedia.add(new MediaResource(MediaResourceType.AUDIO, audioUrl));
		}
		article.setResources(articleMedia);
		return article;

	}

	public List<Article> convertToArticles(Collection<ArticleDTO> dtos) {

		final List<Article> result = new ArrayList<>();

		if (CollectionUtils.isNonEmpty(dtos)) {
			dtos.forEach(dto -> result.add(convert(dto)));
		}

		return result;

	}

	public List<ArticleDTO> convertToDtos(Collection<Article> articles) {

		final List<ArticleDTO> dtos = new ArrayList<>();

		if (CollectionUtils.isNonEmpty(articles)) {
			articles.forEach(article -> dtos.add(convert(article)));
		}

		return dtos;

	}
}
