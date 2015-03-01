DualEditor.markup.H1 = function(contents){
	contents = contents.replace(/^# ([^\s](.*))/igm, "<h1>$1</h1>");
	contents = contents.replace(/^## ([^\s](.*))/igm, "<h2>$1</h2>");
	contents = contents.replace(/^### ([^\s](.*))/igm, "<h3>$1</h3>");
	
	return contents;
}