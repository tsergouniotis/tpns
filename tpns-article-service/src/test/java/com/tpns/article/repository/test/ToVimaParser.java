package com.tpns.article.repository.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.tpns.article.domain.Article;
import com.tpns.article.domain.ArticleStatus;
import com.tpns.article.domain.MediaResource;
import com.tpns.article.domain.MediaResourceType;
import com.tpns.utils.StringUtils;

@ToVimaParserQualifier
public class ToVimaParser implements Parser {

	private static final String INDEX = "http://www.tovima.gr";

	@Override
	public List<Article> parse() {

		final List<Article> results = new ArrayList<>();
		try {

			final Document doc = Jsoup.connect(INDEX).get();

			final Elements level1 = doc.getElementsByTag("div");

			for (final Element e1 : level1) {

				final Elements classItems = e1.getElementsByClass("items");

				for (final Element items : classItems) {

					final Elements item = items.getElementsByAttributeValueContaining("class", "item");

					for (final Element element : item) {

						final Elements as = element.getElementsByTag("a");

						for (final Element a : as) {

							final Elements hrefs = a.getElementsByAttribute("bigurl");

							for (final Element href : hrefs) {

								// String title = hre
								final String link = href.attr("href");

								final String img = href.attr("bigurl");

								final Element span = href.child(1).child(1);

								final String title = span.text();

								if (StringUtils.hasText(title) && title.length() < 256) {

									final String content = parse(INDEX + link);

									final Article article = Article.create(null, title, content);

									article.getResources().add(new MediaResource(MediaResourceType.IMAGE, img));

									article.setStatus(ArticleStatus.PUBLISHED);

									results.add(article);

								}

							}

						}
					}

				}

			}
		} catch (final IOException e) {
			e.printStackTrace();
		}

		return results;
	}

	private String parse(final String node) {
		final StringBuffer sb = new StringBuffer();
		try {
			final Document doc = Jsoup.connect(node).get();

			final Elements level1 = doc.getElementsByClass("article_text");

			for (final Element e1 : level1) {
				return e1.getElementsByAttributeValue("id", "intext_content_tag").text();
			}

		} catch (final IOException e) {
			e.printStackTrace();
		}

		return sb.toString();

	}

}
