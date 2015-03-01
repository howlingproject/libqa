DualEditor.markup.ALERT = function(contents){
	contents = contents.replace(/\[alert\]([\w\W]+?)\[alert\]/gm, "<div class=\"alert alert-warning\"><span class=\"glyphicon glyphicon-chevron-down\"></span>  $1</div>");
	return contents;
}