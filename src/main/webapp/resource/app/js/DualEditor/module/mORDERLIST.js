DualEditor.markup.ORDERLIST = function(contents){
    var reg = /^(<br>)?\d\. (.*)/igm;
    if( contents.match(reg) != null ){
        contents = getOrderListReplaceAll( reg, "ol", contents );
    }
    return contents;
};
DualEditor.markup.UNORDERLIST = function(contents){
    var reg = /^(<br>)?\* (.*)/igm;
    if( contents.match(reg) != null ) {
        contents = getOrderListReplaceAll(reg, "ul", contents);
    }
    return contents;
};


function getOrderListReplaceAll( reg, tag, contents ){
    function replacer(match, p1, p2, offset, string) {
        if( p2 != null ){
            p1 = p2;
        }
        p1 = p1.replace(/<br>/ig, "");
        return "<li>"+p1+"</li>";
    }

    var arrayContents = contents.split("\n");
    var flag = false;

    for( var i = 0; i < arrayContents.length; i++ ){
        if( arrayContents[i].match( reg ) != null ){
            arrayContents[i] = arrayContents[i].replace(reg, replacer);;
            if( !flag ){
                flag = true;
                arrayContents[i] = "<"+tag+">" + arrayContents[i];
            }
        }else if( flag ){
            flag = false;
            arrayContents[i-1] += "</"+tag+">";
        }
    }
    return arrayContents.join("\n");
}