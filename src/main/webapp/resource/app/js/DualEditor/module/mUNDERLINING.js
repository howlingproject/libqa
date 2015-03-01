DualEditor.markup.UNDERLINING = function(contents){
	contents = contents.replace(/([^http:])\/{2}([\w\W]+?)\/{2}/igm, "$1<U>$2</U>");
	return contents;
}