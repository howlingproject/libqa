DualEditor.markup.ITALIC = function(contents){
	contents = contents.replace(/\*([\w\W]+?)\*/gm, "<em>$1</em>");
	contents = contents.replace(/\_([\w\W]+?)\_/gm, "<em>$1</em>");	
	return contents;
}