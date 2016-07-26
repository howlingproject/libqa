DualEditor.markup.LAYOUT = function(contents){

    var pattern = /(<br>)?\[layout\](.*?)\[layout\]\n(<br>)?\[layout\](.*?)\[layout\]\n(<br>)?\[layout\](.*?)\[layout\]\n(<br>)?\[layout\](.*?)\[layout\]/igm;
    contents = contents.replace(pattern, "<div class=\"row\"><div class=\"col-md-3\">$2</div><div class=\"col-md-3\">$4</div><div class=\"col-md-3\">$6</div><div class=\"col-md-3\">$8</div></div>");
    pattern.compile(/(<br>)?\[layout\](.*?)\[layout\]\n(<br>)?\[layout\](.*?)\[layout\]\n(<br>)?\[layout\](.*?)\[layout\]/igm);
    contents = contents.replace(pattern, "<div class=\"row\"><div class=\"col-md-4\">$2</div><div class=\"col-md-4\">$4</div><div class=\"col-md-4\">$6</div></div>");
    pattern.compile(/(<br>)?\[layout\](.*?)\[layout\]\n(<br>)?\[layout\](.*?)\[layout\]/igm);
    contents = contents.replace(pattern, "<div class=\"row\"><div class=\"col-md-6\">$2</div><div class=\"col-md-6\">$4</div></div>");
    return contents;
};