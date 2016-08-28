DualEditor.markup.TABLE = function(contents){
    var reg =  /^(<br>)?\|(.*)\|/igm;
    var arrayContents = contents.split("\n");
    var flag = false;
    var startIdx =-1;
    var endIdx =-1;
    for( var i = 0; i < arrayContents.length; i++ ){
        if( arrayContents[i].match( reg ) != null ){
            if( !flag ){
                flag = true;
                startIdx = i;
            }

        }else if( flag ){
            flag = false;
            endIdx= i;
        }

        if( (startIdx >= 0 && endIdx > 0) && endIdx - startIdx >= 2 ){
            arrayContents = tableReplaceAll(arrayContents, startIdx, endIdx);
            startIdx = -1;
            endIdx = -1;
        }

    }
    return arrayContents.join("\n");

    function tableReplaceAll( arrayContents, startIdx, endIdx ){
        var tempCont = arrayContents.slice();
        var tempCont2 = arrayContents.slice();
        var flag = false;
        var splitSize = tempCont[startIdx].replace(/^<br>/ig, "").split("|").length;

        var table = "";
        for( var i = startIdx; i < endIdx; i++ ){
            if( i == startIdx ){
                table = tableData( tempCont[i], "th", splitSize );
                table = "<table class=\"table table-bordered table-striped\">\n"+
                "   <thead>\n"+
                "      <tr>\n"+
                "           "+table+
                "      </tr>\n"+
                "   </thead>\n";
                tempCont2[i] = table;
            }else if( i == startIdx+1 ){
                var tdArr = tempCont[i].replace(/^<br>/ig, "").split("|");
                if( tdArr.length != tempCont[startIdx].replace(/^<br>/ig, "").split("|").length ){
                    return arrayContents;
                }
                for( var th in tdArr ){
                    if( tdArr[th].match(/(\s+)?(:)?-+(:)?(\s+)?/ig) == null && tdArr[th].trim() != "" ){
                        return arrayContents;
                    }
                }
                tempCont2[i] = "";
            }else{
                var tbody = tableData( tempCont[i], "td", splitSize );
                tbody ="      <tr>\n"+
                "           "+tbody+
                "      </tr>\n";

                if( !flag ){
                    flag = true;
                    tbody = "   <tbody>\n"+tbody;
                }
                if( i == endIdx-1  ){
                    tbody +="   </tbody>\n"+
                    "</table>";
                }
                tempCont2[i] = tbody;
            }
        }
        return tempCont2;

    }

    function tableData( cont, tag, size ){
        var aligns = [];
        var tdArr = cont.replace(/^<br>/ig, "").split("|");
        for( var i = 1; i < size-1; i++ ){
            aligns.push("<"+tag+">"+tdArr[i]+"</"+tag+">")
        }
        return aligns.join("\n");
    }
};