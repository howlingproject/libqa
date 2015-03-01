DualEditor.markup.FIELD = function(contents){
	contents = contents.replace(/\[field(\|([\w\W]+?))?\]([\w\W]+?)\[field\]/gm, "<div class=\"panel panel-default\"><div class=\"panel-heading\"><h3 class=\"panel-title\">$2</h3></div><div class=\"panel-body\">$3</div></div>");
	return contents;
}