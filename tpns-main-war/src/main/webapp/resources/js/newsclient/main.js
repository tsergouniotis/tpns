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
}) 

function printCategories(data) { 	
	var output="<ul>";
    for (var i in data) {
    	output+="<li><a href=\"#\">" + data[i].name + "</a></li>";
	}
	output+="</ul>";
    document.getElementById("catMenu").innerHTML = output;
}