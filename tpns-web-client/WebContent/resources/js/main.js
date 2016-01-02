$(document).ready(function(){
	setLinkToAdmin();
	loadCategories();
	loadAllArticles();	
}) 

function setLinkToAdmin(){
	document.getElementById("adminLinkContainer").innerHTML = "<a class=\"adminLinkButton\" href=\""+articleServiceAddress+"app-admin\/\">Admin</a>";
}

function loadCategories(){
	$.ajax({ 
        type: "GET",
        url: articleServiceAddress+"article-service/v1/category",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json; charset=utf-8',      
        },        
        beforeSend: function(xhr) {
            xhr.setRequestHeader('Authorization', 'Basic ' + window.btoa(unescape(encodeURIComponent('onsports:onsports'))))
        },
        dataType: "json",
        success: function(data) {
            printCategories(data);
        }
    });		
}

function printCategories(data) { 	
	var output="<ul>";
    for (var i in data) {
    	output+="<li>";
    	output+="<a href=\"javascript:loadArticlesByCategory('";
    	output+=data[i];
    	output+="');\">";
    	output+=data[i];
    	output+="</a>";
    	output+"</li>";
	}
	output+="</ul>";
    document.getElementById("catMenu").innerHTML = output;
}

function loadAllArticles(){
	$.ajax({ 
        type: "GET",
        url: articleServiceAddress+"article-service/v1/article/published",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json; charset=utf-8'
        },            
        dataType: "json",
        beforeSend: function(xhr) {
            xhr.setRequestHeader('Authorization', 'Basic ' + window.btoa(unescape(encodeURIComponent('onsports:onsports'))))
        },
        success: function(data) {
        	printArticles(data);
        }
    });	
}

function loadArticlesByCategory(category){
	$.ajax({ 
        type: "GET",
        url: articleServiceAddress+"article-service/v1/article/findPublishedByCategory?catName="+category,
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json; charset=utf-8'
        },            
        dataType: "json",
        beforeSend: function(xhr) {
            xhr.setRequestHeader('Authorization', 'Basic ' + window.btoa(unescape(encodeURIComponent('onsports:onsports'))))
        },
        success: function(data) {
        	printArticles(data);
        }
    });	
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
    	articleOutput+="<a href=\"javascript:loadArticleById('"+data[i].id+"')\"><img class=\"thumbnail\"  height=\"150\" width=\"250\" src=\"";
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
	    headlineContent += "<h2><a href=\"javascript:loadArticleById('"+data[headlineindex].id+"')\">"+data[headlineindex].subject+"</a></h2>"
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

function loadArticleById(articleId){
	window.location.href = window.location.origin+"/article.html?articleId="+articleId;
}

