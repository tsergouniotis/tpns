var articleServiceAddress = "http:\/\/localhost:8081\/";

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
	window.location.href = window.location.origin+"/index.html"
}
