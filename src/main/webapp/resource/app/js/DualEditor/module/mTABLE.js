DualEditor.markup.TABLE = function(contents){
    var editText = contents;

    var regTitle = /^\|\|(.*)\|\|/igm;
    var regTitle2 = /\|\|(.*)\|\|/igm;
    var regBody = /\|(.*)\|/igm;
    var regBody2 = /\|(.*)\|/igm;
    var arrayContents = editText.split("<br>");
    var flag = false ;
    for( var i = 0; i < arrayContents.length; i++ ){
        // 셀제목
        if( arrayContents[i].match( regTitle ) != null ){
            var idx = 0;
            while( arrayContents[i].match( regTitle2 ) != null  ){
                if( idx == 0){
                    arrayContents[i] = arrayContents[i].replace(regTitle2,"<tr class=\"active\">\n<td>\n$1\n</td>\n</tr>");
                }else{
                    arrayContents[i] = arrayContents[i].replace(regTitle2,"\n</td>\n<td>\n$1\n</td>\n<td>");
                }
                idx++;
            }
            arrayContents[i] = arrayContents[i].replace("||","\n</td>\n<td>\n");
            arrayContents[i] = "<thead>" + arrayContents[i] + "</thead>"
            if( !flag ){
                flag = true;
                arrayContents[i] = "<table class=\"table table-bordered\">\n" + arrayContents[i];
            }
            // 셀제목 끝
        }else if( arrayContents[i].match( regBody ) != null ){
            // 로우
            flag = true;
            var idx = 0;
            while( arrayContents[i].match( regBody2 ) != null  ){
                if( idx == 0){
                    arrayContents[i] = arrayContents[i].replace(regBody2,"<tr>\n<td>\n$1\n</td>\n</tr>");
                }else{
                    arrayContents[i] = arrayContents[i].replace(regBody2,"\n</td>\n<td>\n$1\n</td>\n<td>");
                }
                idx++;
            }
            arrayContents[i] = arrayContents[i].replace("|","\n</td>\n<td>\n");
            arrayContents[i] = "<tbody>" + arrayContents[i] + "</tbody>"
            if( !flag ){
                flag = true;
                arrayContents[i] = "<table class=\"table table-bordered\">\n" + arrayContents[i];
            }
            // 로우 끝
        }else if( flag ){
            arrayContents[i-1] = arrayContents[i-1] + "\n</table>";
            flag = false;
        }

    }
    return arrayContents.join("<br>");
}