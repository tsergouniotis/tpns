$(document).ready(function(){
	setLinkToAdmin();
	$.ajax({ 
        type: "GET",
        url: "http://localhost:8080/article-service/v1/category",
        contentType: "application/json; charset=utf-8",
        accept: "application/json",
        dataType: "json",
        success: function(data) {
            printCategories(data);
        }
    });		
	$.ajax({ 
        type: "GET",
        url: "http://localhost:8080/article-service/v1/article",
        contentType: "application/json; charset=utf-8",
        accept: "application/json",
        dataType: "json",
        success: function(data) {
        	printArticles(data);
        }
    });		
}) 

function setLinkToAdmin(){
	var adminLocation = window.location.href +"pages/admin/index.xhtml";	
	document.getElementById("adminLinkContainer").innerHTML = "<a class=\"adminLinkButton\" href=\""+adminLocation+"\">Admin</a>";
}

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
	var hasArticle = false;
	var article1 = "";
	var article2 = "";
	var article3 = "";
	var article4 = "";
	var article5 = "";
	var article6 = "";	
    for (var i in data) {
    	if (count==0){
    		hasArticle = true;
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
    	if ((count%6) ==0){
    		article1 = articleOutput;
    	}
    	if ((count%6) ==1){
    		article2 = articleOutput;
    	}
    	if ((count%6) ==2){
    		article3 = articleOutput;
    	}
    	if ((count%6) ==3){
    		article4 = articleOutput;
    	}
    	if ((count%6) ==4){
    		article5 = articleOutput;
    	}
    	if ((count%6) ==5){
    		article6 = articleOutput;
    	}
    	count = count + 1;
	}
    var headlineImage = "";
    var headlineContent = "";
	if (hasArticle){
		var headlineindex = count-1; 
		headlineImage = "<img src=\""+getImageFromArticle(data[headlineindex])+"\" alt=\"\" />"
	    headlineContent += "<h2><a href=\"#\">"+data[headlineindex].subject+"</a></h2>"
	    headlineContent += "<p>"+data[headlineindex].shortDescription+"</p>"
	} else {
		article1 = "<p>No articles found!</p>";
		article2 = "<p>Visit the admin page to load more articles</p>";
	}
    document.getElementById("headlineimageDiv").innerHTML = headlineImage;
    document.getElementById("headlineContentDiv").innerHTML = headlineContent;

    document.getElementById("topArticleContainer").innerHTML = article1+article2+article3;
    document.getElementById("bottomArticleContainer").innerHTML = article4+article5+article6;
}

function getImageFromArticle(article){
	if (article.imageUrls.length>0){
		return article.imageUrls[0];
	}
	return "http://www.optv.org/s/photogallery/img/no-image-available.jpg";
}


