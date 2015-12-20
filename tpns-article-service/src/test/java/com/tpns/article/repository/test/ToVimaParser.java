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

		List<Article> results = new ArrayList<>();
		try {

			Document doc = Jsoup.connect(INDEX).get();

			Elements level1 = doc.getElementsByTag("div");

			for (Element e1 : level1) {

				Elements classItems = e1.getElementsByClass("items");

				for (Element items : classItems) {

					Elements item = items.getElementsByAttributeValueContaining("class", "item");

					for (Element element : item) {

						Elements as = element.getElementsByTag("a");

						for (Element a : as) {

							Elements hrefs = a.getElementsByAttribute("bigurl");

							for (Element href : hrefs) {

								// String title = hre
								String link = href.attr("href");

								String img = href.attr("bigurl");

								Element span = href.child(1).child(1);

								String title = span.text();

								if (StringUtils.hasText(title) && title.length() < 256) {

									String content = parse(INDEX + link);

									Article article = Article.create(null, title, content);

									article.getResources().add(new MediaResource(MediaResourceType.IMAGE, img));

									article.setStatus(ArticleStatus.PUBLISHED);

									results.add(article);

								}

							}

						}
					}

				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return results;
	}

	private String parse(String node) {
		StringBuffer sb = new StringBuffer();
		try {
			Document doc = Jsoup.connect(node).get();

			Elements level1 = doc.getElementsByClass("article_text");

			for (Element e1 : level1) {
				return e1.getElementsByAttributeValue("id", "intext_content_tag").text();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();

	}

}
