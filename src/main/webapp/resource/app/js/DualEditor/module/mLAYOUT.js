DualEditor.markup.LAYOUT = function(contents){
    var pattern = /\[layout1\]([\w\W]+?)\[layout1\](\n|<br>)?\[layout2\]([\w\W]+?)\[layout2\](\n|<br>)?\[layout3\]([\w\W]+?)\[layout3\](\n|<br>)?\[layout4\]([\w\W]+?)\[layout4\]/igm;
	contents = contents.replace(pattern, "<div class=\"row\">\r\n<div class=\"col-md-3\">\r\n$1\r\n</div>\r\n<div class=\"col-md-3\">\r\n$3\r\n</div>\r\n<div class=\"col-md-3\">\r\n$5\r\n</div>\r\n<div class=\"col-md-3\">\r\n$7\r\n</div>\r\n</div>\r\n");
    pattern.compile(/\[layout1\]([\w\W]+?)\[layout1\](\n|<br>)?\[layout2\]([\w\W]+?)\[layout2\](\n|<br>)?\[layout3\]([\w\W]+?)\[layout3\]/igm);
    contents = contents.replace(pattern, "<div class=\"row\">\r\n<div class=\"col-md-4\">\r\n$1\r\n</div>\r\n<div class=\"col-md-4\">\r\n$3\r\n</div>\r\n<div class=\"col-md-4\">\r\n$5\r\n</div>\r\n</div>\r\n");
    pattern.compile(/\[layout1\]([\w\W]+?)\[layout1\](\n|<br>)?\[layout2\]([\w\W]+?)\[layout2\]/igm);
    contents = contents.replace(pattern, "<div class=\"row\">\r\n<div class=\"col-md-6\">\r\n$1\r\n</div>\r\n<div class=\"col-md-6\">\r\n$3\r\n</div>\r\n</div>\r\n");
	return contents;
}