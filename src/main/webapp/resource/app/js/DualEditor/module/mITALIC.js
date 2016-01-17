DualEditor.markup.ITALIC = function(contents){
	contents = contents.replace(/\_([\w\W]+?)\_/gm, "<em>$1</em>");	
	return contents;
};