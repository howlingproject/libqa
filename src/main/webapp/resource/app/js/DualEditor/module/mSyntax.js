DualEditor.markup.SYNTAX = function(contents){
    var idx = 0;
    function replacer(match, p1, p2, offset, string) {
        var type = ["Apache","Bash","CoffeeScript","C++","C","C#","CSS","Diff","HTTP","Ini","Java","JavaScript","JSON","Makefile","Markdown","Nginx","Objective-C","Perl","PHP","Python","Ruby","SQL","HTML","XML"];
        var data = syntaxData[idx];
        p1 = p1.replace(/</gm, "&lt;");
        data = data.replace(/<br>/ig, "");
        for( var i=0; i< type.length; i++ ){
            if( type[i] == p1 ){
                idx++;
                return "<pre><code2 class=\"hljs "+p1+"\">"+data+"</code2></pre>";
            }
        }
        return p2;
    }
    contents = contents.replace(/\[syntax ([\w\W]+?)\]([\w\W]+?)\[syntax\]/gm, replacer);
	return contents;
};

DualEditor.markup.SYNTAX_BEFORE = function(contents){
    var idx = 0;
    function replacer(match, p1, p2, offset, string) {
        syntaxData.push(p2);
        idx++;
        return "[syntax "+p1+"]  [syntax]";
    }
    contents.replace(/\[syntax ([\w\W]+?)\]([\w\W]+?)\[syntax\]/gm, replacer);
    return contents;
};
