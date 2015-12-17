$(document).ready(function(){
	setLinkToMain();
}) 


function loadAllArticles(aritcleId){
	$.ajax({ 
        type: "GET",
        url: window.location.origin+"/article-service/v1/article/"+aritcleId,
        contentType: "application/json; charset=utf-8",
        accept: "application/json",
        dataType: "json",
        success: function(data) {
        	printArticles(data);
        }
    });	
}

function printArticles(data) { 	
    document.getElementById("articleTitle").innerHTML = "Article by replace ment";
    document.getElementById("articleImage").innerHTML = "http://www.optv.org/s/photogallery/img/no-image-available.jpg";
    document.getElementById("articleContent").innerHTML = "Article Content by replacement";
}

function setLinkToMain(){
	var adminLocation = window.location.origin+"/news";	
	document.getElementById("backContainer").innerHTML = "<a class=\"backToMainLink\" href=\""+adminLocation+"\">Back</a>";
}
