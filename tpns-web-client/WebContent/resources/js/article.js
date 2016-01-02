$(document).ready(function(){
	var articleId = getParameterByName('articleId');
	loadArticle(articleId);
}) 


function loadArticle(articleId){
	$.ajax({ 
        type: "GET",
        url: articleServiceAddress+"article-service/v1/article/"+articleId,
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
