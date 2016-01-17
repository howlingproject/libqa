DualEditor.markup.SYNTAX = function(contents){
    function replacer(match, p1, offset, string) {
        p1 = p1.replace(/</gm, "&lt;");
        return "<figure class=\"highlight\"><pre><code>"+p1+"</code></pre></figure>";
    }
    contents = contents.replace(/\[syntax\]([\w\W]+?)\[syntax\]/gm, replacer);
	return contents;
};