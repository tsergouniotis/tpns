package com.tpns.article.converters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tpns.article.domain.Article;
import com.tpns.article.domain.ArticleStatus;
import com.tpns.article.domain.Category;
import com.tpns.article.domain.MediaResource;
import com.tpns.article.domain.MediaResourceType;
import com.tpns.article.dto.ArticleDTO;
import com.tpns.utils.CollectionUtils;

public class ArticleConverter implements Serializable {

	private static final long serialVersionUID = -3865348678015399264L;

	public ArticleDTO toDto(final Article article) {

		if (null == article) {
			return null;
		}

		final ArticleDTO dto = new ArticleDTO();
		dto.setId(article.getId());
		dto.setSubject(article.getSubject());
		dto.setShortDescription(article.getShortDescription());
		dto.setContent(article.getContent());

		if (null != article.getCategory()) {
			dto.setCategory(article.getCategory().getName());
		}

		dto.setCreatedAt(article.getCreatedAt());
		dto.setUpdatedAt(article.getUpdatedAt());
		dto.setPostedAt(article.getPostedAt());
		dto.setAuthor(article.getAuthor());
		final ArticleStatus status = article.getStatus();

		if (null != status) {
			dto.setStatus(status.toString());
		}

		final List<String> imageUrls = new ArrayList<>();

		final List<String> videoUrls = new ArrayList<>();

		final List<String> audioUrls = new ArrayList<>();

		for (final MediaResource mediaResource : article.getResources()) {
			switch (mediaResource.getType()) {
			case IMAGE:
				imageUrls.add(mediaResource.getUrl());
				break;
			case VIDEO:
				videoUrls.add(mediaResource.getUrl());
				break;
			case AUDIO:
				audioUrls.add(mediaResource.getUrl());
				break;
			}
		}

		dto.setImageUrls(imageUrls);

		dto.setVideoUrls(videoUrls);

		dto.setAudioUrls(audioUrls);

		return dto;
	}

	public Article toEntity(final ArticleDTO dto) {

		final Article article = Article.create(dto.getSubject(), dto.getShortDescription(), dto.getContent(), Category.create(dto.getCategory()), dto.getAuthor(),
				ArticleStatus.valueOf(dto.getStatus()), dto.getCreatedAt(), dto.getUpdatedAt(), dto.getPostedAt(), dto.getDestinations(), toMediaResources(dto));

		return article;

	}

	private static List<MediaResource> toMediaResources(final ArticleDTO dto) {
		final List<MediaResource> result = new ArrayList<MediaResource>();
		for (final String imageUrl : dto.getImageUrls()) {
			result.add(new MediaResource(MediaResourceType.IMAGE, imageUrl));
		}
		for (final String videoUrl : dto.getVideoUrls()) {
			result.add(new MediaResource(MediaResourceType.VIDEO, videoUrl));
		}
		for (final String audioUrl : dto.getAudioUrls()) {
			result.add(new MediaResource(MediaResourceType.AUDIO, audioUrl));
		}
		return result;
	}

	public List<Article> toEntities(final Collection<ArticleDTO> dtos) {

		final List<Article> result = new ArrayList<>();

		if (CollectionUtils.isNonEmpty(dtos)) {
			dtos.forEach(dto -> result.add(toEntity(dto)));
		}

		return result;

	}

	public List<ArticleDTO> toDtos(final Collection<Article> articles) {

		final List<ArticleDTO> dtos = new ArrayList<>();

		if (CollectionUtils.isNonEmpty(articles)) {
			articles.forEach(article -> dtos.add(toDto(article)));
		}

		return dtos;

	}
}
