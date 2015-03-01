DualEditor.markup.SUPERSCRIPT = function(contents){
	contents = contents.replace(/\[sb\]([\w\W]+?)\[sb\]/gm, "<sub>$1</sub>");
	return contents;
}