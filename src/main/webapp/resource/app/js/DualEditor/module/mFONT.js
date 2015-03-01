DualEditor.markup.FONT = function(contents){
	var rex = /\[font\|(.*?)\]([\w\W]+?)\[font\]/gm;
	if( contents.match(rex) == null ) return contents;
	var font = contents.match(rex).toString().replace(rex, "$1");

	var fontType;
	if( "돋움" == font ){
		fontType = "dotum";
	}else if( "굴림" == font ){
		fontType = "gulim";
	}else if( "궁서" == font ){
		fontType = "gungsuh";
	}else if( "바탕" == font ){
		fontType = "batang";
	}

	contents = contents.replace(rex, "<span style=\"font-family:'"+font+"','"+fontType+"'\">$2\r\n</span>");
	return contents;
}

DualEditor.markup.FONTSIZE = function(contents){
	contents = contents.replace(/\[size\|(.*?)\]([\w\W]+?)\[size\]/igm, "<span style=\"font-size: $1px;\">$2\r\n</span>");
	return contents;
}

DualEditor.markup.FONTSTYLE = function(contents){
	var rex = /(h1\.(\s{0,}))(.*)?/igm;
	if( contents.match(rex) != null ){
		contents = contents.replace(rex, "<h2>$3</h2>");
	}
	
	var rex = /(h2\.(\s{0,}))(.*)?/igm;
	if( contents.match(rex) != null ){
		contents = contents.replace(rex, "<h3>$3</h3>");
	}
	
	var rex = /(h3\.(\s{0,}))(.*)?/igm;
	if( contents.match(rex) != null ){
		contents = contents.replace(rex, "<h4>$3</h4>");
	}
	
	return contents;
}


