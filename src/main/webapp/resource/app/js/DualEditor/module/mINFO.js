DualEditor.markup.INFO = function(contents){
	contents = contents.replace(/\[info\]([\w\W]+?)\[info\]/gm, "<div class=\"alert alert-info\"><span class=\"glyphicon glyphicon-ok\"></span>  $1</div>");
	return contents;
}