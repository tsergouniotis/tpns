$(document).ready(function(){
	var data = { "categories":[
	                           {
	                               "name":"politics",
	                           },
	                           {
	                               "name":"economy",
	                           },  
	                           {
	                               "name":"sports",
	                           },
	                           {
	                               "name":"technology",
	                           },
	                           {
	                               "name":"social",
	                           }
	                   ]};
	printCategories(data);
}) 

function printCategories(data) { 	
	var output="<ul>";
    for (var i in data.categories) {
    	output+="<li><a href=\"#\">" + data.categories[i].name + "</a></li>";
	}
	output+="</ul>";
    document.getElementById("catMenu").innerHTML = output;
}