DualEditor.markup.DEL = function(contents){
    contents = contents.replace(/~~([\w\W]+?)~~/gm, "<del>$1</del>");
	return contents;
};