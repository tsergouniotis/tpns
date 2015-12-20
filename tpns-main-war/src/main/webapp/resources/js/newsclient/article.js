$(document).ready(function(){
	var articleId = getParameterByName('articleId');
	loadArticle(articleId);
}) 


function loadArticle(articleId){
	$.ajax({ 
        type: "GET",
        url: window.location.origin+"/article-service/v1/article/"+articleId,
        contentType: "application/json; charset=utf-8",
        accept: "application/json",
        dataType: "json",
        success: function(data) {
        	printArticle(data);
        }
    });	
}

function printArticle(data) { 	
    document.getElementById("articleTitle").innerHTML = "<span  class=\"articleHeader\"><h2>"+data.subject+"</h2></span>";
    var articleContent ="";
    articleContent+="<img class=\"articleImg\" src=\"";
    articleContent+=getImageFromArticle(data);
    articleContent+="\" />";
    articleContent+="<p class=\"articleText\" >"+data.content+"</p>";		
    document.getElementById("articleContentDiv").innerHTML =  articleContent;
}


function getImageFromArticle(article){
	if (article.imageUrls.length>0){
		return article.imageUrls[0];
	}
	return "http://www.optv.org/s/photogallery/img/no-image-available.jpg";
}

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

function backToHomePage(){
	window.location.href = window.location.origin+"/news"
}
