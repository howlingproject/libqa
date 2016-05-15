DualEditor.markup.ALIGN = function(contents){
	contents = contents.replace(/\[align\:(left|right|center)\]([\w\W]+?)\[align\]/gm, "<div style='text-align:$1'>$2</div>");
	return contents;
};