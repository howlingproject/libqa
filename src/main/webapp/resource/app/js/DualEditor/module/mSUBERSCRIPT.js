DualEditor.markup.SUBERSCRIPT = function(contents){
	contents = contents.replace(/\[sp\]([\w\W]+?)\[sp\]/gm, "<sup>$1</sup>");
	return contents;
}