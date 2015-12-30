package com.tpns.article.repository.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.tpns.article.domain.Article;
import com.tpns.article.domain.MediaResource;
import com.tpns.article.domain.MediaResourceType;

@News24ParserQualifier
public class News24Parser implements Parser {

	public static final String INDEX = "http://www.news247.gr";

	@Override
	public List<Article> parse() {

		final List<Article> result = new ArrayList<>();

		try {
			final Document doc = Jsoup.connect(INDEX).get();
			final Elements stories = doc.getElementsByClass("stories");

			for (final Element story : stories) {

				final Elements articles = story.getElementsByClass("article");

				for (final Element article : articles) {

					final Article tmp = toArticle(article);
					if (null != tmp) {
						result.add(tmp);
					}

				}
			}

		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}

	private Article toArticle(final Element element) {
		try {
			final String link = element.getElementsByTag("a").first().attr("href");

			final String img = element.getElementsByTag("img").first().attr("src");

			final String title = element.getElementsByTag("h2").first().text();

			element.getElementsByClass("summary").first().getElementsByTag("p").first().text();

			final Article article = parse(link);

			article.getResources().add(new MediaResource(MediaResourceType.IMAGE, img));

			article.setSubject(title);

			return article;

		} catch (final Exception e) {

			return null;
		}

	}

	private Article parse(final String link) {

		try {
			final Document doc = Jsoup.connect(link).get();
			final Element story = doc.getElementsByAttributeValue("itemprop", "articleBody").first();

			final Elements elems = story.getElementsByClass("storyContent");

			final Element storyContent1 = elems.get(0);
			final Element prologue = storyContent1.child(0);
			final String description = prologue.text();

			final Element storyContent2 = elems.get(1);
			final Element body = storyContent2.child(0);

			final StringBuilder builder = new StringBuilder();
			final Elements children = body.children();
			for (final Element child : children) {
				builder.append(child.html());
			}

			final String content = body.text();// builder.toString();

			final Article result = Article.create(null, null, content);

			result.setShortDescription(description);

			return result;

		} catch (final IOException e) {
			return null;
		}

	}

}
