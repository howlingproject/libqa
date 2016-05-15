DualEditor.markup.HR = function(contents){
	contents = contents.replace(/^<br>\*\*\*$/igm, "<hr>");
	return contents;
};