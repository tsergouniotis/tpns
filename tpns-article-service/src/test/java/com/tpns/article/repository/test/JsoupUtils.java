package com.tpns.article.repository.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.tpns.article.domain.Article;

public final class JsoupUtils {

	private JsoupUtils() {

	}

	public static List<Article> parseIndexPage(String node) {

		List<Article> results = new ArrayList<>();
		try {

			Document doc = Jsoup.connect(node).get();

			Elements level1 = doc.getElementsByTag("div");

			for (Element e1 : level1) {

				Elements level2 = e1.getElementsByClass("items");

				for (Element e2 : level2) {

					Elements level3 = e2.getElementsByTag("a");
					for (Element e3 : level3) {
						Elements hrefs = e3.getElementsByAttribute("bigurl");
						for (Element href : hrefs) {

							// String title = hre
							String link = href.attr("href");
							Element span = href.child(1).child(1);// ("id",
																	// "title");
							String title = span.text();

							String content = parseArticle(node + link);

							Article article = Article.create(null, title, content);

							results.add(article);
						}
					}

				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return results;
	}

	private static String parseArticle(String node) {
		StringBuffer sb = new StringBuffer();
		try {
			Document doc = Jsoup.connect(node).get();

			Elements level1 = doc.getElementsByClass("article_text");

			for (Element e1 : level1) {
				Elements level2 = e1.getElementsByAttributeValue("id", "intext_content_tag");
				for (Element e2 : level2) {
					// System.out.println(e2);
					sb.append(e2.toString());

					// System.out.println(sb.toString());
				}
			}
			// sb.toString();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sb.toString();

	}

}
