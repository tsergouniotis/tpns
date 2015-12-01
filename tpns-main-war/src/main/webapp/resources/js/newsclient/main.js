$(document).ready(function(){
	$.ajax({ 
        type: "GET",
        url: "http://localhost:8081/article-service/v1/category",
        contentType: "application/json; charset=utf-8",
        accept: "application/json",
        dataType: "json",
        success: function(data) {
            printCategories(data);
        }
    });		
	$.ajax({ 
        type: "GET",
        url: "http://localhost:8081/article-service/v1/article",
        contentType: "application/json; charset=utf-8",
        accept: "application/json",
        dataType: "json",
        success: function(data) {
        	printArticles(data);
        }
    });		
}) 

function printCategories(data) { 	
	var output="<ul>";
    for (var i in data) {
    	output+="<li><a href=\"#\">" + data[i].name + "</a></li>";
	}
	output+="</ul>";
    document.getElementById("catMenu").innerHTML = output;
}

function printArticles(data) { 	
	var count=0;
	var articleOutput;
	var topComponentContext ="<p>No articles found!</p>";
	var bottomComponentContext ="<p>Visit the admin page to load more articles</p>";
    for (var i in data) {
    	if (count == 0) {
    		topComponentContext ="";
    	}
    	if (count == 3 ) {
    		bottomComponentContext ="";
    	}
    	if ((count%3) ==0){
    		articleOutput="<article class=\"tripleblocks tripleleftblock\">";
    	}
    	if ((count%3) ==1){
    		articleOutput="<article class=\"tripleblocks triplemiddleblock\">";
    	}
    	if ((count%3) ==2){
    		articleOutput="<article class=\"tripleblocks triplerightblock\">";
    	}
    	articleOutput+="<a href=\"#\"><img class=\"thumbnail\"  height=\"150\" width=\"250\" src=\"";
    	articleOutput+= getImageFromArticle(data[i]);
    	articleOutput+= "\" \"/>";
    	articleOutput+="<span class=\"caption\"><b>"
    	articleOutput+= data[i].subject;
    	articleOutput+= "</b></span></a>";
    	articleOutput+="<p>"
    	articleOutput+=	data[i].shortDescription
    	articleOutput+= "</p>";
    	articleOutput+="</article>";
    	if ((count%6)<3){
    		topComponentContext+=articleOutput;
    	} else {
    		bottomComponentContext+=articleOutput;
    	}
    	count = count + 1;
	}
    document.getElementById("topArticleContainer").innerHTML = topComponentContext;
    document.getElementById("bottomArticleContainer").innerHTML = bottomComponentContext;
}

function getImageFromArticle(article){
	if (article.imageUrls.length>0){
		return article.imageUrls[0];
	}
	return "http://www.optv.org/s/photogallery/img/no-image-available.jpg";
}


