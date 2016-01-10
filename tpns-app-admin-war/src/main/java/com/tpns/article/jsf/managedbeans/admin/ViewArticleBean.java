package com.tpns.article.jsf.managedbeans.admin;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tpns.article.converters.ArticleConverter;
import com.tpns.article.domain.Article;
import com.tpns.article.domain.ArticleStatus;
import com.tpns.article.domain.MediaResource;
import com.tpns.article.domain.MediaResourceType;
import com.tpns.article.dto.ArticleDTO;
import com.tpns.article.jsf.model.Role;
import com.tpns.article.jsf.utils.JSFUtils;
import com.tpns.article.managers.ArticleManager;
import com.tpns.article.managers.CategoryManager;
import com.tpns.article.services.ArticleService;
import com.tpns.article.services.CategoryService;
import com.tpns.common.domain.errors.BusinessException;
import com.tpns.utils.StringUtils;

@ManagedBean
@ViewScoped
public class ViewArticleBean extends BaseTpnsBean implements Serializable {

	private static final Logger LOGGER = LoggerFactory.getLogger(ViewArticleBean.class);
	
	private static final String news24Author="news24";

	private static final long serialVersionUID = 6512180737722350944L;

	@EJB
	private CategoryService categoryService;
	@EJB
	private ArticleService articleService;

	@EJB
	private CategoryManager categoryManager;
	@EJB
	private ArticleManager articleManager;
	
	@Inject
	private ArticleConverter articleConverter;

	@ManagedProperty(value = "#{userSessionBean}")
	private UserSessionBean userSessionBean;

	private ArticleDTO selectedArticle;

	private List<ArticleDTO> availableArticles;
	private Map<String, String> articleStatusDisplay;
	private boolean hasNews24;

	@PostConstruct
	public void init() {
		this.availableArticles = new ArrayList<ArticleDTO>();
		for (ArticleDTO article : articleService.findAll()) {
			// if own article under edit
			if (article.getAuthor().equals(userSessionBean.getUser().getId()) && ArticleStatus.CREATED.toString().equals(article.getStatus())) {
				availableArticles.add(article);
				// else add only posted and above
			} else if (!ArticleStatus.CREATED.toString().equals(article.getStatus())) {
				availableArticles.add(article);
			}
			if (!hasNews24 && news24Author.equals(article.getAuthor())){
				hasNews24 = true;
			}
		}
		initStatuses();
	}

	private void initStatuses() {
		this.articleStatusDisplay = new HashMap<String, String>();
		this.articleStatusDisplay.put(ArticleStatus.CREATED.toString(), JSFUtils.getMessageFromMessageBundle(FacesContext.getCurrentInstance(), "article.status.underedit"));
		this.articleStatusDisplay.put(ArticleStatus.POSTED.toString(), JSFUtils.getMessageFromMessageBundle(FacesContext.getCurrentInstance(), "article.status.readyforreview"));
		this.articleStatusDisplay.put(ArticleStatus.REVIEWED.toString(), JSFUtils.getMessageFromMessageBundle(FacesContext.getCurrentInstance(), "article.status.readyforpublish"));
		this.articleStatusDisplay.put(ArticleStatus.PUBLISHED.toString(), JSFUtils.getMessageFromMessageBundle(FacesContext.getCurrentInstance(), "article.status.publish"));
	}

	public String getArticleStatus(String status) {
		String statusString = articleStatusDisplay.get(status);
		return StringUtils.isEmptyString(statusString) ? status : statusString;
	}

	public boolean isEditAllowed(String author, String status) {
		return (author.equals(userSessionBean.getUser().getUsername()) && ArticleStatus.CREATED.toString().equals(status)) || (userSessionBean.getUser().hasRole(Role.CHIEF_EDITOR));
	}

	/*
	 * JSF Actions
	 */

	public String editArticle() {
		return "/pages/editArticle.xhtml";
	}

	public String deleteArticle() {
		if (null == selectedArticle) {
			LOGGER.error("Unexpectidely asked to delete with no article selected");
		} else {
			articleService.delete(selectedArticle.getId());
			availableArticles.remove(selectedArticle);
			selectedArticle = null;
		}
		return null;
	}
	
	public boolean isLoadArticlesAllowed() {
		return (userSessionBean.getUser().hasRole(Role.CHIEF_EDITOR)) && !hasNews24;
	}
	
	public boolean isDeletAllAllowed() {
		return (userSessionBean.getUser().hasRole(Role.CHIEF_EDITOR));
	}
	
	public String loadFromNews24(){
		final List<Article> articles = parse();
		LOGGER.info("Found "+ +articles.size()+ "articles ");
		int count =0;
		for (final Article article : articles) {
			article.setAuthor(news24Author);
			article.setCategory(categoryManager.getByName("politics"));
			if (StringUtils.hasText(article.getShortDescription()) && article.getShortDescription().length() > 512) {
				article.setShortDescription(article.getShortDescription().substring(0, 511));
			}
			article.setStatus(ArticleStatus.PUBLISHED);
			try {
				articleManager.save(article);
				count++;
			} catch (final BusinessException e) {
				LOGGER.info("Skipping article with title ["+article.getSubject()+"]");
				//e.printStackTrace();
			}				
		}
		hasNews24 = true;
		LOGGER.info("Added "+ count + "articles ");
		return "/pages/index.xhtml";
	}
	
	public String deleteAll(){

		for (final ArticleDTO article : availableArticles) {
			articleManager.delete(article.getId());			
		}
		availableArticles.clear();
		hasNews24 = false;
		return null;
	}	

	/*
	 * Getter - setters
	 */
	public List<ArticleDTO> getAvailableArticles() {
		return availableArticles;
	}

	public void setAvailableArticles(List<ArticleDTO> availableArticles) {
		this.availableArticles = availableArticles;
	}

	public ArticleDTO getSelectedArticle() {
		return selectedArticle;
	}

	public void setSelectedArticle(ArticleDTO selectedArticle) {
		this.selectedArticle = selectedArticle;
	}

	public UserSessionBean getUserSessionBean() {
		return userSessionBean;
	}

	public void setUserSessionBean(UserSessionBean userSessionBean) {
		this.userSessionBean = userSessionBean;
	}
	
	
	private List<Article> parse() {

		final List<Article> result = new ArrayList<>();

		try {
			final Document doc = Jsoup.connect("http://www.news247.gr").get();
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
