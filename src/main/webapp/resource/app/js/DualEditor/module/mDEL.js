DualEditor.markup.DEL = function(contents){
	contents = contents.replace(/\[d\]([\w\W]+?)\[d\]/gm, "<span style=\"text-decoration:line-through;\">$1</span>");
	return contents;
}