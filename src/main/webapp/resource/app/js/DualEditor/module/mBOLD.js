DualEditor.markup.BOLD = function(contents){
	contents = contents.replace(/\*{2}([\w\W]+?)\*{2}/gm, "<strong>$1</strong>");
	contents = contents.replace(/\_{2}([\w\W]+?)\_{2}/gm, "<strong>$1</strong>");
	return contents;
}