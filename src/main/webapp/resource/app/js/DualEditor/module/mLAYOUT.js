DualEditor.markup.LAYOUT = function(contents){
    function replacer(match, p1, p2, offset, string) {
        if( idx == 0 ){
            p2 = "<div class=\"row\"><div class=\"col-md-"+(7-layoutSize)+"\">"+p2+"</div>";
        }else{
            p2 = "<div class=\"col-md-"+(7-layoutSize)+"\">"+p2+"</div>";
        }
        idx++;
        return p2;
    }
    if( contents.match(/(<br>)?\[layout\]([\W\w]+?)\[layout\]/igm) != null ){
        var layoutSize = contents.match(/(<br>)?\[layout\]([\W\w]+?)\[layout\]/igm).length;
        var idx = 0;
        if( layoutSize != null && layoutSize > 1 && layoutSize <= 4 ){
            contents = contents.replace(/(<br>)?\[layout\]([\W\w]+?)\[layout\]/igm, replacer);
        }
    }
    return contents;
};