DualEditor.markup.LAYOUT = function(contents){
    var pattern = /^\[layout\](.*?)\[layout\]\n\[layout\](.*?)\[layout\]\n\[layout\](.*?)\[layout\]\n\[layout\](.*?)\[layout\]/igm;
    contents = contents.replace(pattern, "<div class=\"row\"><div class=\"col-md-3\">$1</div><div class=\"col-md-3\">$2</div><div class=\"col-md-3\">$3</div><div class=\"col-md-3\">$4</div></div>");
    pattern.compile(/^\[layout\](.*?)\[layout\]\n\[layout\](.*?)\[layout\]\n\[layout\](.*?)\[layout\]/igm);
    contents = contents.replace(pattern, "<div class=\"row\"><div class=\"col-md-4\">$1</div><div class=\"col-md-4\">$2</div><div class=\"col-md-4\">$3</div></div>");
    pattern.compile(/^\[layout\](.*?)\[layout\]\n\[layout\](.*?)\[layout\]/igm);
    contents = contents.replace(pattern, "<div class=\"row\"><div class=\"col-md-6\">$1</div><div class=\"col-md-6\">$2</div></div>");
    return contents;
}