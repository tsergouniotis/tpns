package com.tpns.article.repository.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.tpns.article.domain.Article;
import com.tpns.article.domain.MediaResource;
import com.tpns.article.domain.MediaResourceType;

@News24ParserQualifier
public class News24Parser implements Parser {

	public static final String INDEX = "http://www.news247.gr";

	@Test
	public void test() {

		new News24Parser().parse();
	}

	@Override
	public List<Article> parse() {

		List<Article> result = new ArrayList<>();

		try {
			Document doc = Jsoup.connect(INDEX).get();
			Elements stories = doc.getElementsByClass("stories");

			for (Element story : stories) {

				Elements articles = story.getElementsByClass("article");

				for (Element article : articles) {

					Article tmp = toArticle(article);
					if (null != tmp) {
						result.add(tmp);
					}

				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}

	private Article toArticle(Element element) {
		try {
			String link = element.getElementsByTag("a").first().attr("href");

			String img = element.getElementsByTag("img").first().attr("src");

			String title = element.getElementsByTag("h2").first().text();

			String desc = element.getElementsByClass("summary").first().getElementsByTag("p").first().text();

			Article article = parse(link);

			article.getResources().add(new MediaResource(MediaResourceType.IMAGE, img));

			article.setSubject(title);

			return article;

		} catch (Exception e) {

			return null;
		}

	}

	private Article parse(String link) {

		try {
			Document doc = Jsoup.connect(link).get();
			Element story = doc.getElementsByAttributeValue("itemprop", "articleBody").first();

			Elements elems = story.getElementsByClass("storyContent");

			Element storyContent1 = elems.get(0);
			Element prologue = storyContent1.child(0);
			String description = prologue.text();

			Element storyContent2 = elems.get(1);
			Element body = storyContent2.child(0);

			StringBuilder builder = new StringBuilder();
			Elements children = body.children();
			for (Element child : children) {
				builder.append(child.html());
			}

			String content = body.text();//builder.toString();

			Article result = Article.create(null, null, content);

			result.setShortDescription(description);

			return result;

		} catch (IOException e) {
			return null;
		}

	}

}
