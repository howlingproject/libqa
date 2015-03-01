DualEditor.markup.ORDERLIST = function(contents){
    var editText = contents;

    var reg = /^[\d]. (.*)/igm;
    var reg2 = /^\s{1,}[\d]. (.*)/igm;
    editText = getOrderListReplaceAll(reg, reg2, "ol", editText);
    editText = getOrderListReplaceAll(reg2, reg, "ol", editText);

    return editText;;
}
DualEditor.markup.UNORDERLIST = function(contents){
    var editText = contents;

    var reg = /^[\*\-\+] (.*)/igm;
    var reg2 = /^\s{1,}[\*\-\+] (.*)/igm;
    editText = getOrderListReplaceAll(reg, reg2, "ul", editText);
    editText = getOrderListReplaceAll(reg2, reg, "ul", editText);

    return editText;
}

function getOrderListReplaceAll( reg, reg2, tag, editText ){
    var arrayContents = editText.split("<br>");
    var flag = false;
    for( var i = 0; i < arrayContents.length; i++ ){
        if( arrayContents[i].match( reg ) != null ){
            if( flag ){
                arrayContents[i] = "<li>" + arrayContents[i].replace(reg, "$1" ) + "</li>\n" ;
            }else{
                flag = true;
                arrayContents[i] = "\n<"+tag+">\n<li>" + arrayContents[i].replace(reg, "$1" ) + "</li>\n" ;
            }

        }else if( arrayContents[i].match( reg2 ) != null ){
            arrayContents[i] = arrayContents[i];

        }else if( flag ){
            arrayContents[i-1] = arrayContents[i-1] + "\n</"+tag+">\n";
            flag = false;
        }
    }
    return arrayContents.join("<br>");
}