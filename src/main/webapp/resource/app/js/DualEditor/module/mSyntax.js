DualEditor.markup.SYNTAX = function(contents){
    function replacer(match, p1, p2, offset, string) {
        var type = ["applescript","as3","shell","cf","c","c#","css","delphi","diff","erl","groovy","java","javafx","js","pl","php","text","py","ruby","sass","scala","sql","vb","html"];
        p1 = p1.replace(/</gm, "&lt;");
        p2 = p2.replace(/<br>/ig, "");
        for( var i=0; i< type.length; i++ ){
            if( type[i] == p1 ){
                return "<pre class=\"brush: "+p1+"\">"+p2+"</pre>";
            }
        }
        return p2.replace(/</gm, "&lt;");
    }
    contents = contents.replace(/\[syntax ([\w\W]+?)\]([\w\W]+?)\[syntax\]/gm, replacer);
	return contents;
};