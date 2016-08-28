DualEditor.markup.LINK = function(contents){
    contents = contents.replace(/[!]\[(.*)\]\(([\w\W]+?)\)/igm,
        function(match, p1,p2){
            p2 = p2.replace(/<em>|<\/em>/igm,"_")
            return "<p><img src=\""+p2+"\" style=\"max-width:100%; height: auto;\" alt=\""+p1+"\"></p>";
        }
    );
    contents = contents.replace(/\[(.*)\]\(([\w\W]+?)\)/igm,
        function(match, p1,p2){
            p1 = p1.replace(/<em>|<\/em>/igm,"_")
            return "<a href=\""+p2+"\" target=\"_blank\">"+p1+"</a>";
        }
    );
    return contents;
};