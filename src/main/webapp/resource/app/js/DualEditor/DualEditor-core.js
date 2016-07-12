var DualEditor = (function(){
    var DualEditor = {};
    DualEditor.markup = {};

    options = {
        src : ''
        ,tarketId : ''
        ,type : ''
        ,width : ''
        ,height : ''
        ,data : ''

    };

    DualEditor.markup = function(contents){

        contents = contents.replace(/\n|\r\n/ig, "\n<br>");
        contents = DualEditor.markup.ITALIC( contents );
        contents = DualEditor.markup.FIELD( contents );
        contents = DualEditor.markup.ALERT( contents );
        contents = DualEditor.markup.INFO( contents );
        contents = DualEditor.markup.LINK( contents );
        contents = DualEditor.markup.TABLE( contents );
        contents = DualEditor.markup.ORDERLIST( contents );
        contents = DualEditor.markup.UNORDERLIST( contents );

        contents = DualEditor.markup.H1( contents );
        contents = DualEditor.markup.HR( contents );
        contents = DualEditor.markup.LAYOUT( contents );
        contents = DualEditor.markup.SYNTAX( contents );

        contents = DualEditor.markup.FONT( contents );
        contents = DualEditor.markup.FONTSIZE( contents );
        contents = DualEditor.markup.FONTSTYLE( contents );
        contents = DualEditor.markup.ALIGN( contents );
        contents = DualEditor.markup.BOLD( contents );
        contents = DualEditor.markup.DEL( contents );
        contents = DualEditor.markup.UNDERLINING( contents );
        contents = DualEditor.markup.SUPERSCRIPT( contents );
        contents = DualEditor.markup.SUBERSCRIPT( contents );

        return contents;
    };


    DualEditor.append = function(src){
        loadJQuery(src+"/js/DualEditor/util/fn-editor-util.js");
        loadJQuery(src+"/js/DualEditor/util/fn-editor-layer.js");
        loadJQuery(src+"/js/DualEditor/util/fn-block-range.js");
        loadJQuery(src+"/js/DualEditor/util/fn-editor.js");

        loadJQuery(src+"/js/DualEditor/module/mFONT.js");
        loadJQuery(src+"/js/DualEditor/module/mALIGN.js");
        loadJQuery(src+"/js/DualEditor/module/mBOLD.js");
        loadJQuery(src+"/js/DualEditor/module/mITALIC.js");
        loadJQuery(src+"/js/DualEditor/module/mDEL.js");
        loadJQuery(src+"/js/DualEditor/module/mUNDERLINING.js");
        loadJQuery(src+"/js/DualEditor/module/mSUPERSCRIPT.js");
        loadJQuery(src+"/js/DualEditor/module/mSUBERSCRIPT.js");
        loadJQuery(src+"/js/DualEditor/module/mH1.js");
        loadJQuery(src+"/js/DualEditor/module/mHR.js");
        loadJQuery(src+"/js/DualEditor/module/mFIELD.js");
        loadJQuery(src+"/js/DualEditor/module/mALERT.js");
        loadJQuery(src+"/js/DualEditor/module/mINFO.js");
        loadJQuery(src+"/js/DualEditor/module/mLINK.js");
        loadJQuery(src+"/js/DualEditor/module/mTABLE.js");
        loadJQuery(src+"/js/DualEditor/module/mORDERLIST.js");
        loadJQuery(src+"/js/DualEditor/module/mLAYOUT.js");
        loadJQuery(src+"/js/DualEditor/module/mSyntax.js");

        loadCSS(src+"/js/DualEditor/syntaxhiglight/styles/shThemeDefault.css");
        loadCSS(src+"/js/DualEditor/syntaxhiglight/styles/shCore.css");
        loadJQuery(src+"/js/DualEditor/syntaxhiglight/scripts/shCore.js");
        loadJQuery(src+"/js/DualEditor/syntaxhiglight/scripts/shBrushXml.js");
        loadJQuery(src+"/js/DualEditor/syntaxhiglight/scripts/shBrushJScript.js");
        loadJQuery(src+"/js/DualEditor/syntaxhiglight/scripts/shBrushAppleScript.js");
        loadJQuery(src+"/js/DualEditor/syntaxhiglight/scripts/shBrushAS3.js");
        loadJQuery(src+"/js/DualEditor/syntaxhiglight/scripts/shBrushBash.js");
        loadJQuery(src+"/js/DualEditor/syntaxhiglight/scripts/shBrushCpp.js");
        loadJQuery(src+"/js/DualEditor/syntaxhiglight/scripts/shBrushCSharp.js");
        loadJQuery(src+"/js/DualEditor/syntaxhiglight/scripts/shBrushCss.js");
        loadJQuery(src+"/js/DualEditor/syntaxhiglight/scripts/shBrushDelphi.js");
        loadJQuery(src+"/js/DualEditor/syntaxhiglight/scripts/shBrushDiff.js");
        loadJQuery(src+"/js/DualEditor/syntaxhiglight/scripts/shBrushErlang.js");
        loadJQuery(src+"/js/DualEditor/syntaxhiglight/scripts/shBrushGroovy.js");
        loadJQuery(src+"/js/DualEditor/syntaxhiglight/scripts/shBrushJava.js");
        loadJQuery(src+"/js/DualEditor/syntaxhiglight/scripts/shBrushJavaFX.js");
        loadJQuery(src+"/js/DualEditor/syntaxhiglight/scripts/shBrushPerl.js");
        loadJQuery(src+"/js/DualEditor/syntaxhiglight/scripts/shBrushPhp.js");
        loadJQuery(src+"/js/DualEditor/syntaxhiglight/scripts/shBrushPlain.js");
        loadJQuery(src+"/js/DualEditor/syntaxhiglight/scripts/shBrushPython.js");
        loadJQuery(src+"/js/DualEditor/syntaxhiglight/scripts/shBrushRuby.js");
        loadJQuery(src+"/js/DualEditor/syntaxhiglight/scripts/shBrushSass.js");
        loadJQuery(src+"/js/DualEditor/syntaxhiglight/scripts/shBrushScala.js");
        loadJQuery(src+"/js/DualEditor/syntaxhiglight/scripts/shBrushSql.js");
        loadJQuery(src+"/js/DualEditor/syntaxhiglight/scripts/shBrushVb.js");
    };


    DualEditor.setting = function(params){
        for (var prop in params) {
            if ((prop in options)) {
                options[prop] = params[prop];
            }
        }

        DualEditor.append(options.src);
        var target = $("#"+options.tarketId);
        if( options.type == "mini"){
            target.append(getMarkupEditMiniHtml(options.width, options.height));
        }else{
            target.append(getMarkupEditHtml(options.width, options.height));
            //에디터 사이즈설정.
            var screenWidth = window.screen.width;
            $(".sonDualEditor tbody :first").attr("style", "width:"+screenWidth+"px;");
            $(".sonDualEditor tbody :first > td").attr("style", "width:"+screenWidth/2+"px;");
        }



        //스크룰링 싱크 wikimaincol
        var $divs = $('#wikiEditor');
        var sync = function(e){
            var master = this, other = document.getElementById("wikimaincol");
            var percentage = master.scrollTop / (master.scrollHeight - master.offsetHeight);
            other.scrollTop = percentage * (other.scrollHeight - other.offsetHeight);
        };
        $divs.on( 'scroll', sync);

        $("#wikiEditor").val(options.data);
        var editor = document.getElementById("wikiEditor");		// [object HTMLTextAreaElement]

        // 각 에디터 버튼 클릭시 액션 처리
        $(".sonDualEditor #btnToolbar .btn-group .btn-sm").each(function(){
            $(this).on("click", function() {
                var $me = $(this);
                var data = $me.data();
                // 에디터 액션 처리
                $.editorAction(editor, $me, data);
                DualEditor.markup.parsing();
            });
        });

        // 각 에디터 버튼 클릭시 액션 처리
        $(".sonDualEditor #btnToolbar .btn-group .dropdown-menu a").each(function(){
            $(this).on("click", function() {
                var $me = $(this);
                var data = $me.data();
                // 에디터 액션 처리
                $.editorAction(editor, $me, data);
                DualEditor.markup.parsing();
            });
        });

        $("#wikiEditor").keydown(function(evnet){
            DualEditor.markup.parsing();
        });

        $(".nav-pills a").on("click", function() {
            $(this).tab('show');
            var id = $(this).attr("href");
            var next = $(id).find(".active");
            $(next).removeClass("active");
            $(next).find("a").trigger('click');
        });

    };

    DualEditor.markup.parsing = function(){
        setTimeout(function() {
            $("#wikimaincol").text("");
            var txt = DualEditor.markup( $("#wikiEditor").val() );
            $("#wikimaincol").html( "<div style=\"width:96%\">"+txt+"</div>" );
            SyntaxHighlighter.all();
        }, 1000);

    };

    DualEditor.getMarkupEditHtml = function(){
        var target = $("#wikiEditor");
        return target.val();
    };

    DualEditor.getMarkupConvertToHtml = function(){
        var target = $("#wikimaincol > div");
        return target.html();
    };

    return DualEditor;
})();

function loadJQuery(src) {
    var oScript = document.createElement("script");
    oScript.type = "text/javascript";
    oScript.charset = "utf-8";
    oScript.src = src;
    document.getElementsByTagName("head")[0].appendChild(oScript);
}

function loadCSS(src) {
    var linkElm = document.createElement("link");
    linkElm.rel = "stylesheet";
    linkElm.href = src;
    document.getElementsByTagName("head")[0].appendChild(linkElm);
}

function getMarkupEditHtml(width, height){
    var style = "";
    style = width == '' ? style + '' : style + "width:"+width+";";
    style = height == '' ? style + '' : style + "height:"+height+";";

    var html =
        "<table class=\"sonDualEditor\" style=\""+style+"\">" +
        "   <thead>" +
        "   <tr>" +
        "       <th colspan=\"2\">" +
        "           <div class=\"btn-toolbar\" id=\"btnToolbar\">" +
        "               <div class=\"btn-group\">" +
        "                   <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm dropdown-toggle\" data-toggle=\"dropdown\"><i class=\"fa fa-header\"></i><span class=\"caret\"></span></button>" +
        "                   <ul class=\"dropdown-menu\" role=\"menu\">" +
        "                       <li>" +
        "                           <a data-mode=\"font\" data-before=\"# \" data-center=\"\"  data-after=\"\" unselectable=\"on\">" +
        "                               <span style=\"font-size:18px;\">h1. 큰 헤드라인</span>" +
        "                           </a>" +
        "                           <a data-mode=\"font\" data-before=\"## \" data-center=\"\"  data-after=\"\" unselectable=\"on\">" +
        "                               <span style=\"font-size:14px;\">h2. 중간 헤드라인</span>" +
        "                           </a>" +
        "                           <a data-mode=\"font\" data-before=\"### \" data-center=\"\"  data-after=\"\" unselectable=\"on\">" +
        "                               <span style=\"font-size:12px;\">h3. 작은 헤드라인</span>" +
        "                           </a>" +
        "                       </li>" +
        "                   </ul>" +
        "               </div>" +
        "               <div class=\"btn-group\">" +
        "                   <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-mode=\"append\" data-before=\"**\" data-center=\" \" data-after=\"**\"><i class=\"fa fa-bold\"></i></button>" +
        "                   <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-mode=\"append\" data-before=\"_\" data-center=\" \" data-after=\"_\"><i class=\"fa fa-italic\"></i></button>" +
        "                   <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-mode=\"append\" data-before=\"//\" data-center=\" \" data-after=\"//\"><i class=\"fa fa-underline\"></i></button>" +
        "                   <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-mode=\"append\" data-before=\"~~\" data-center=\" \" data-after=\"~~\"><i class=\"fa fa-strikethrough\"></i></button>" +
        "                   <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-mode=\"append\" data-before=\"[sb]\" data-center=\" \" data-after=\"[sb]\"><i class=\"fa fa-subscript\"></i></button>" +
        "                   <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-mode=\"append\" data-before=\"[sp]\" data-center=\" \" data-after=\"[sp]\"><i class=\"fa fa-superscript\"></i></button>" +
        "                   <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-mode=\"append\" data-before=\"[align:left]\" data-center=\" \" data-after=\"[align]\"><i class=\"fa fa-align-left\"></i></button>" +
        "                   <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-mode=\"append\" data-before=\"[align:center]\" data-center=\" \" data-after=\"[align]\"><i class=\"fa fa-align-center\"></i></button>" +
        "                   <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-mode=\"append\" data-before=\"[align:right]\" data-center=\" \" data-after=\"[align]\"><i class=\"fa fa-align-right\"></i></button>" +
        "               </div>" +
        "               <div class=\"btn-group\">" +
        "                   <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-mode=\"insert\" data-before=\"***\" data-center=\"\" data-after=\"\"><i class=\"glyphicon glyphicon-minus\"></i></button>" +
        "               </div>" +
        "               <div class=\"btn-group\">" +
        "               </div>" +
        "               <div class=\"btn-group\">" +
        "                   <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-mode=\"append\" data-before=\"* \" data-center=\" \" data-after=\"\"><i class=\"fa fa-list-ul\"></i></button>" +
        "                   <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-mode=\"append\" data-before=\"1. \" data-center=\" \" data-after=\"\"><i class=\"fa fa-list-ol\"></i></button>" +
        "                   <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-toggle=\"modal\" data-target=\"#syntaxModal\" data-mode=\"layer\" data-type=\"syntax\" ><i class=\"fa fa-code\"></i></button>" +
        "                   <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-toggle=\"modal\" data-target=\"#tableModal\" data-mode=\"layer\" data-type=\"table\"><i class=\"fa fa-table\"></i></button>" +
        "                   <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-mode=\"append\" data-before=\"[field|타이틀]\" data-center=\"\"  data-after=\"[field]\"><i class=\"fa fa-credit-card\"></i></button>" +
        "                   <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-toggle=\"modal\" data-target=\"#urlModal\" data-mode=\"layer\" data-type=\"url\" ><i class=\"fa fa-link\"></i></button>" +
        "                   <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-toggle=\"modal\" data-target=\"#imgModal\" data-mode=\"layer\" data-type=\"img\" ><i class=\"fa fa-file-image-o\"></i></button>" +
        "                   <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-mode=\"append\" data-before=\"[alert]\" data-center=\" \" data-after=\"[alert]\"><i class=\"fa fa-exclamation-triangle\"></i></button>" +
        "                   <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-mode=\"append\"  data-before=\"[info]\"  data-center=\" \"  data-after=\"[info]\"><i class=\"fa fa-info\"></i></button>" +
        "               </div>" +
        "               <div class=\"btn-group\">" +
        "                   <button type=\"button\" class=\"dualEditorWiki-btn btn btn-default btn-sm dropdown-toggle\" data-toggle=\"dropdown\" data-mode=\"columns\"><i class=\"fa fa-columns\"></i></button>" +
        "                   <a type=\"button\" class=\"dualEditorWiki-btn btn btn-default btn-sm\" data-toggle=\"collapse\" href=\"#hepl\"><i class=\"fa fa-lightbulb-o\"></i></a>" +
        "               </div>" +
        "           </div>" +
        "           <hr style=\"background-color:#ccc;height: 1px;margin-top: 10px;margin-bottom: 12px;\"> " +
        "			<div class=\"collapse\" id=\"hepl\" style=\"height : 100px;\">"+
        "				<div class=\"col-md-2\" style=\"height : 100px; overflow: auto\">"+
        "					<ul class=\"nav nav-pills nav-stacked edit-nav edit-nav-pills\">"+
        "						<li class=\"active\"><a href=\"#Itext\" data-toggle=\"pill\" >텍스트 마크업</a></li>"+
        "						<li><a href=\"#Ilist\" data-toggle=\"pill\" >정렬 마크업</a></li>"+
        "						<li><a href=\"#Iarert\" data-toggle=\"pill\" >알림 마크업</a></li>"+
        "						<li><a href=\"#Ispecial\" data-toggle=\"pill\" >특수 마크업</a></li>"+
        "					</ul>"+
        "				</div>"+
        "				<div class=\"tab-content col-md-2\" style=\"height : 100px; overflow: auto\">"+
        "					<div id=\"Itext\" class=\"tab-pane fade in active\">"+
        "						<ul class=\"nav nav-pills nav-stacked edit-nav edit-nav-pills\">"+
        "							<li class=\"active\"><a href=\"#Ib\" data-toggle=\"pill\" >굵게</a></li>"+
        "							<li><a href=\"#Ii\" data-toggle=\"pill\" >기울기</a></li>"+
        "							<li><a href=\"#Iu\" data-toggle=\"pill\" >밑줄</a></li>"+
        "							<li><a href=\"#Id\" data-toggle=\"pill\" >취소선</a></li>"+
        "							<li><a href=\"#Isub\" data-toggle=\"pill\" >아랫첨자</a></li>"+
        "							<li><a href=\"#Isup\" data-toggle=\"pill\" >윗첨자</a></li>"+
        "							<li><a href=\"#Istyle\" data-toggle=\"pill\" >폰트 스타일</a></li>"+
        "							<li><a href=\"#Ifont\" data-toggle=\"pill\" >폰트 크기</a></li>"+
        "							<li><a href=\"#Ihead\" data-toggle=\"pill\" >폰트 헤드</a></li>"+
        "							<li><a href=\"#Icolor\" data-toggle=\"pill\" >폰트 색상</a></li>"+
        "						</ul>"+
        "					</div>"+
        "					<div id=\"Ilist\" class=\"tab-pane fade\">"+
        "						<ul class=\"nav nav-pills nav-stacked\">"+
        "							<li class=\"active\"><a href=\"#Imark\" data-toggle=\"pill\" >마크순서</a></li>"+
        "							<li><a href=\"#Inum\" data-toggle=\"pill\" >숫자순서</a></li>"+
        "							<li><a href=\"#Ileft\" data-toggle=\"pill\" >왼쪽정렬</a></li>"+
        "							<li><a href=\"#Icenter\" data-toggle=\"pill\" >가운데정렬</a></li>"+
        "							<li><a href=\"#Iright\" data-toggle=\"pill\" >오른쪽정렬</a></li>"+
        "						</ul>"+
        "					</div>"+
        "					<div id=\"Iarert\" class=\"tab-pane fade\">"+
        "						<ul class=\"nav nav-pills nav-stacked edit-nav edit-nav-pills\">"+
        "							<li class=\"active\"><a href=\"#Ifield\" data-toggle=\"pill\" >필드</a></li>"+
        "							<li><a href=\"#Ialer\" data-toggle=\"pill\" >알림</a></li>"+
        "							<li><a href=\"#Iinfo\" data-toggle=\"pill\" >정보</a></li>"+
        "						</ul>"+
        "					</div>"+
        "					<div id=\"Ispecial\" class=\"tab-pane fade\">"+
        "						<ul class=\"nav nav-pills nav-stacked edit-nav edit-nav-pills\">"+
        "							<li class=\"active\"><a href=\"#Ihr\" data-toggle=\"pill\" >문단선</a></li>"+
        "							<li><a href=\"#Isy\" data-toggle=\"pill\" >신택스하이라이트</a></li>"+
        "							<li><a href=\"#Itab\" data-toggle=\"pill\" >테이블</a></li>"+
        "							<li><a href=\"#Ilin\" data-toggle=\"pill\" >링크 이미지, URL</a></li>"+
        "						</ul>"+
        "					</div>"+
        "				</div>"+
        "				<div class=\"tab-content col-md-5\"style=\"height : 100px; overflow: auto\">"+
        "					<div id=\"Ib\" class=\"well tab-pane fade in active\">"+
        "						Syntax : <code>** **</code><br>"+
        "						Example : <code>**bold**</code><br>"+
        "						output : <strong>bold</strong>"+
        "					</div>"+
        "					<div id=\"Ii\" class=\"well tab-pane fade\">"+
        "						Syntax : <code>_ _</code><br>"+
        "						Example : <code>_italicized_</code><br>"+
        "						output : <em>italicized</em>"+
        "					</div>"+
        "					<div id=\"Iu\" class=\"well tab-pane fade\">"+
        "						Syntax : <code>// //</code><br>"+
        "						Example : <code>//italicized//</code><br>"+
        "						output : <u>italicized</u>"+
        "					</div>"+
        "					<div id=\"Id\" class=\"well tab-pane fade\">"+
        "						Syntax : <code>~~ ~~</code><br>"+
        "						Example : <code>~~delete~~</code><br>"+
        "						output : <del>italicized</del>"+
        "					</div>"+
        "					<div id=\"Isub\" class=\"well tab-pane fade\">"+
        "						Syntax : <code>^ ^</code><br>"+
        "						Example : <code>^SUBERSCRIPT^</code><br>"+
        "						output : <sub>SUBERSCRIPT</sub>"+
        "					</div>"+
        "					<div id=\"Isup\" class=\"well tab-pane fade\">"+
        "						Syntax : <code>~ ~</code><br>"+
        "						Example : <code>~SUPERSCRIPT~</code><br>"+
        "						output : <sup>SUPERSCRIPT</sup>"+
        "					</div>"+
        "					<div id=\"Istyle\" class=\"well tab-pane fade\">"+
        "						Syntax : <code>[font|굴림] [font]</code><br>"+
        "						Example : <code>[font|굴림]font-style[font]</code><br>"+
        "						output : <span style=\"font-family:'굴림','gulim'\">굴림체</span>"+
        "					</div>"+
        "					<div id=\"Ifont\" class=\"well tab-pane fade\">"+
        "						Syntax : <code>[size|16] [size]</code><br>"+
        "						Example : <code>[size|16]font-size[size]</code><br>"+
        "						output : <span style=\"font-size: 16px;\">font-size</span>"+
        "					</div>"+
        "					<div id=\"Ihead\" class=\"well tab-pane fade\">"+
        "						Syntax : <code># or ## or ### </code><br>"+
        "						Example :<br>"+
        "						<code>"+
        "						# h1<br>"+
        "						## h2<br>"+
        "						### h3<br>"+
        "						</code><br>"+
        "						output :"+
        "						<h1>h1</h1>"+
        "						<h2>h2</h2>"+
        "						<h3>h3</h3>"+
        "					</div>"+
        "					<div id=\"Icolor\" class=\"well tab-pane fade\">"+
        "						Syntax : <code>[color|428bca] [color]</code><br>"+
        "						Example : <code>[color|428bca]font-color[color]</code><br>"+
        "						output : <span style=\"color: #428bca\">font-color</span>"+
        "					</div>"+
        "					<div id=\"Imark\" class=\"well tab-pane fade\">"+
        "						Syntax : <code>* 텍스트</code><br>"+
        "						Example : <code><br>"+
        "						* list1<br>"+
        "						* list2<br>"+
        "						* list3<br>"+
        "						</code>"+
        "						output :"+
        "							<ul><li>list1</li>"+
        "							<li>list2</li>"+
        "							<li>list3</li></ul>"+
        "					</div>"+
        "					<div id=\"Inum\" class=\"well tab-pane fade\">"+
        "						Syntax : <code>1. 텍스트</code><br>"+
        "						Example : <code><br>"+
        "						1. list1<br>"+
        "						1. list2<br>"+
        "						1. list3<br>"+
        "						</code>"+
        "						output :"+
        "							<ol><li>list1</li>"+
        "							<li>list2</li>"+
        "							<li>list3</li></ol>"+
        "					</div>"+
        "					<div id=\"Ileft\" class=\"well tab-pane fade\">"+
        "						Syntax : <code>[left] [left]</code><br>"+
        "						Example : <code>[left]leftText[left]</code><br>"+
        "						output : <div style=\"text-align:left\">leftText</div>"+
        "					</div>"+
        "					<div id=\"Icenter\" class=\"well tab-pane fade\">"+
        "						Syntax : <code>[center] [center]</code><br>"+
        "						Example : <code>[center]leftText[center]</code><br>"+
        "						output : <div style=\"text-align:center\">CenterText</div>"+
        "					</div>"+
        "					<div id=\"Iright\" class=\"well tab-pane fade\">"+
        "						Syntax : <code>[right] [right]</code><br>"+
        "						Example : <code>[right]leftText[right]</code><br>"+
        "						output : <div style=\"text-align:right\">RightText</div>"+
        "					</div>"+
        "					<div id=\"Ifield\" class=\"well tab-pane fade\">"+
        "						Syntax : <code>[field|타이틀] [field]</code><br>"+
        "						Example : <code>[field|title]내용[field]</code><br>"+
        "						output :"+
        "							<div class=\"panel panel-default\">"+
        "							<div class=\"panel-heading\"><h3 class=\"panel-title\">title</h3></div>"+
        "							<div class=\"panel-body\">내용</div></div>"+
        "					</div>"+
        "					<div id=\"Ialer\" class=\"well tab-pane fade\">"+
        "						Syntax : <code>[alert] [alert]</code><br>"+
        "						Example : <code>[alert]내용[alert]</code><br>"+
        "						output :"+
        "						<div class=\"alert alert-warning\"><span class=\"glyphicon glyphicon-chevron-down\"></span>내용</div>"+
        "					</div>"+
        "					<div id=\"Iinfo\" class=\"well tab-pane fade\">"+
        "						Syntax : <code>[info] [info]</code><br>"+
        "						Example : <code>[info]내용[info]</code><br>"+
        "						output :"+
        "						<div class=\"alert alert-info\"><span class=\"glyphicon glyphicon-ok\"></span>내용</div>"+
        "					</div>"+
        "					<div id=\"Ihr\" class=\"well tab-pane fade\">"+
        "						Syntax : <code>***</code><br>"+
        "						Example : <code>***</code><br>"+
        "						output : <hr>"+
        "					</div>"+
        "					<div id=\"Isy\" class=\"well tab-pane fade\">"+
        "						Syntax : <code>[syntax] [syntax]</code><br>"+
        "						Example : <code>[syntax]syntax[syntax]</code><br>"+
        "						output : syntax"+
        "					</div>"+
        "					<div id=\"Itab\" class=\"well tab-pane fade\">"+
        "						Example : <code><br>"+
        "						| 타이틀| 타이틀|<br>"+
        "						| ---:| ---:|<br>"+
        "						|data1|data2|<br>"+
        "						|data3|data3|<br>"+
        "						</code>"+
        "						output :"+
        "							<table class=\"table table-bordered table-striped\">"+
        "								<thead>"+
        "								<tr>"+
        "									<th> 타이틀</th>"+
        "									<th> 타이틀</th>      </tr>"+
        "								</thead>"+
        "								<tbody>"+
        "								<tr>"+
        "									<td>123</td>"+
        "									<td>sadf</td>      </tr>"+
        "								<tr>"+
        "									<td>5345</td>"+
        "									<td>bbvcvb</td>      </tr>"+
        "								</tbody>"+
        "							</table>"+
        "					</div>"+
        "					<div id=\"Ilin\" class=\"well tab-pane fade\">"+
        "						Syntax : <code>[lable](url)</code><br>"+
        "						Example : <code>[www.libqa.com](http://www.libqa.com/)</code><br>"+
        "						output : <a href=\"http://11st.co.kr\" target=\"_blank\">www.libqa.com</a><br>"+
        "                                    <br>"+
        "						Syntax : <code>![img text](url)</code><br>"+
        "						Example : <code>![img text](https://daringfireball.net/graphics/logos)</code><br>"+
        "						output : <p><img src=\"https://daringfireball.net/graphics/logos\" style=\"max-width:100%; height: auto;\" alt=\"img text\"></p>"+
        "					</div>"+
        "				</div>"+
        "			</div>"+
        "       </th>" +
        "   </tr>" +
        "   </thead>" +
        "   <tbody>" +
        "   <tr>" +
        "       <td style=\"padding-top: 10px;padding-left: 0px; width: 50%\">" +
        "           <textarea class=\"dualEditor form-control\" rows=\"20\" id=\"wikiEditor\" style=\"overflow: scroll; height: 500px;\"></textarea>" +
        "       </td>" +
        "       <td style=\"padding-top: 10px;padding-left: 10px; width: 50%\">" +
        "           <div id=\"wikimaincol\" style=\"background-color:white; border:1px solid #ccc; padding-top:5px; padding-left:10px; border-radius:5px; overflow: scroll; height: 500px;\">" +
        "       </td>" +
        "   </tr>" +
        "   </tbody>" +
        "</table>";

    return html;


}

function getMarkupEditMiniHtml(width, height){
    var styleWidth = "";
    var styleHeight = "";
    styleWidth += width == '' ? '' : "width:"+width+";";
    styleHeight += height == '' ? '' : "height:"+height+";";

    var html =
    "<div class='dualEditor-nav' role='tabpanel' style=\""+styleWidth+"\">" +
    " 	<!-- Nav tabs --> 	" +
    " 	<ul class='nav nav-tabs ' role='tablist'>" +
    " 	  <li role='presentation' class='active'><a href='#mini-write' aria-controls='write' role='tab' data-toggle='tab'>Write</a></li>" +
    " 	  <li role='presentation'><a href='#mini-preview' aria-controls='preview' role='tab' data-toggle='tab'>Preview</a></li>" +
    " 	</ul>" +
    " 	</div>" +
    " 	 	<!-- Tab panes --> 	" +
    "<div class='tab-content dualEditor-nav' style=\""+styleWidth+"\">" +
    " 	  <div role='tabpanel' class='tab-pane preview' id='mini-preview'>" +
    " 		<div id='wikimaincol' class='dualEditor-preview' style=\""+styleHeight+"\"></div>" +
    " 	  </div>" +
    " 	  <div role='tabpanel' class='tab-pane write active' id='mini-write'>" +
    " 	    <table class='sonDualEditor dualEditor-main' style=\""+styleHeight+"\">" +
    " 		    <thead>" +
    " 				<tr>" +
    "                   <td style='padding: 8px'></td>" +
    "               </tr>" +
    " 				<tr>" +
    " 					<th>" +
    " 						<div class='btn-toolbar' id='btnToolbar'>" +
    " 							<div class='btn-group'>" +
    " 								<button type='button' class='dualEditor-wiki-btn btn btn-default btn-sm dropdown-toggle' data-toggle='dropdown'><i class='fa fa-header'></i><span class='caret'></span></button>" +
    " 								<ul class='dropdown-menu' role='menu'>" +
    " 									<li>" +
    "                                       <a data-mode=\"font\" data-before=\"# \" data-center=\"\"  data-after=\"\" unselectable=\"on\">" +
    "                                           <span style=\"font-size:18px;\">h1. 큰 헤드라인</span>" +
    "                                       </a>" +
    "                                       <a data-mode=\"font\" data-before=\"## \" data-center=\"\"  data-after=\"\" unselectable=\"on\">" +
    "                                           <span style=\"font-size:14px;\">h2. 중간 헤드라인</span>" +
    "                                       </a>" +
    "                                       <a data-mode=\"font\" data-before=\"### \" data-center=\"\"  data-after=\"\" unselectable=\"on\">" +
    "                                           <span style=\"font-size:12px;\">h3. 작은 헤드라인</span>" +
    "                                       </a>" +
    " 									</li>" +
    " 								</ul>" +
    " 							</div>" +
    " 							<div class='btn-group'>" +
    " 								<button type='button' class='dualEditor-wiki-btn btn btn-default btn-sm' data-mode='append' data-before='**' data-center=' ' data-after='**'><i class='fa fa-bold'></i></button>" +
    " 								<button type='button' class='dualEditor-wiki-btn btn btn-default btn-sm' data-mode='append' data-before='_' data-center=' ' data-after='_'><i class='fa fa-italic'></i></button>" +
    " 								<button type='button' class='dualEditor-wiki-btn btn btn-default btn-sm' data-mode='append' data-before='//' data-center=' ' data-after='//'><i class='fa fa-underline'></i></button>" +
    " 								<button type='button' class='dualEditor-wiki-btn btn btn-default btn-sm' data-mode='append' data-before='[d]' data-center=' ' data-after='[d]'><i class='fa fa-strikethrough'></i></button>" +
    " 							</div>" +
    " 							<div class='btn-group'>" +
    " 								<button type='button' class='dualEditor-wiki-btn btn btn-default btn-sm' data-mode='append' data-before='* ' data-center=' ' data-after=''><i class='fa fa-list-ul'></i></button>" +
    " 								<button type='button' class='dualEditor-wiki-btn btn btn-default btn-sm' data-mode='append' data-before='1. ' data-center=' ' data-after=''><i class='fa fa-list-ol'></i></button>" +
    " 								<button type='button' class='dualEditor-wiki-btn btn btn-default btn-sm' data-mode='append' data-before='[syntax]' data-center=''  data-after='[syntax]'><i class='fa fa-code'></i></button>" +
    " 								<button type='button' class='dualEditor-wiki-btn btn btn-default btn-sm' data-toggle='modal' data-target='#tableModal' data-mode='layer' data-type='table'><i class='fa fa-table'></i></button>" +
    " 								<button type='button' class='dualEditor-wiki-btn btn btn-default btn-sm' data-toggle='modal' data-target='#urlModal' data-mode='layer' data-type='url' ><i class='fa fa-link'></i></button>" +
    " 								<button type='button' class='dualEditor-wiki-btn btn btn-default btn-sm' data-toggle='modal' data-target='#imgModal' data-mode='layer' data-type='img' ><i class='fa fa-file-image-o'></i></button>" +
    "                               <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-toggle=\"modal\" data-target=\"#layoutModal\" data-mode=\"layer\" data-type=\"layout\" ><i class=\"fa fa-columns\"></i></button>" +
    " 							</div>" +
    " 						</div>" +

    " 					</th>" +
    " 				</tr>" +
    " 				<tr><td style='padding: 8px'></td></tr>" +
    " 			</thead>" +
    " 			<tbody>" +
    " 				<tr>" +
    " 					<td>" +
    " 						<textarea class='dualEditor form-control' rows='5' id='wikiEditor' name='we_wiki_text' ></textarea>" +
    " 					</td>" +
    " 				</tr>" +
    " 			</tbody>" +
    " 		</table>" +
    " 	  </div>" +
    " 	</div>";

    return html;
}