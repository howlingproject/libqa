var DualEditor = (function(){
    var DualEditor = {};
    DualEditor.markup = {};

    options = {
        src : ''
        ,tarketId : ''
        ,type : ''
        ,width : ''
        ,height : ''

    };

    DualEditor.markup = function(contents){
        contents = DualEditor.markup.H1( contents );
        contents = DualEditor.markup.HR( contents );
        contents = DualEditor.markup.LAYOUT( contents );
        contents = contents.replace(/(^\s*)|(\s*$)/g, "" ).replace(/\n/ig, "<br>");
        contents = DualEditor.markup.FONT( contents );
        contents = DualEditor.markup.FONTSIZE( contents );
        contents = DualEditor.markup.FONTSTYLE( contents );
        contents = DualEditor.markup.ALIGN( contents );
        contents = DualEditor.markup.BOLD( contents );
        contents = DualEditor.markup.ITALIC( contents );
        contents = DualEditor.markup.DEL( contents );
        contents = DualEditor.markup.UNDERLINING( contents );
        contents = DualEditor.markup.SUPERSCRIPT( contents );
        contents = DualEditor.markup.SUBERSCRIPT( contents );

        contents = DualEditor.markup.FIELD( contents );
        contents = DualEditor.markup.ALERT( contents );
        contents = DualEditor.markup.INFO( contents );
        contents = DualEditor.markup.LINK( contents );
        contents = DualEditor.markup.TABLE( contents );
        contents = DualEditor.markup.ORDERLIST( contents );
        contents = DualEditor.markup.UNORDERLIST( contents );

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
        }



        //스크룰링 싱크 wikimaincol
        var $divs = $('#wikiEditor');
        var sync = function(e){
            var master = this, other = document.getElementById("wikimaincol");
            var percentage = master.scrollTop / (master.scrollHeight - master.offsetHeight);
            other.scrollTop = percentage * (other.scrollHeight - other.offsetHeight);
        }
        $divs.on( 'scroll', sync);

        $("#wikiEditor").append("마크업 테스트\r\n***\r\n**굵게**\r\n__굵게__\r\n*기울임*\r\n_기울임_\r\n//밑줄//\r\n[d]취소선[d]\r\n[field|필드셋 타이틀]필드셋[field]\r\n[alert]경고[alert]\r\n[info]안내[info]\r\n[sp]아래첨자[sp]\r\n[sb]위첨자[sb]\r\n\r\n||셀제목1||셀제목2||셀제목3||셀제목4||\r\n|컬럼1|컬럼2|컬럼1|컬럼2|\r\n|컬럼3|컬럼4|컬럼3|컬럼4| \r\n\r\n[layout] [field|필드셋 타이틀]필드셋[field] [layout]\n[layout] [alert]경고[alert] [layout]\n[layout] [info]안내[info] [layout]\n[layout] [info]4444[info] [layout]\n[layout] paddig5 [layout]\n[layout] paddig2-1 [layout]\n[layout] paddig2-2 [layout] \r\n\r\n [alert]중간[alert]  \r\n\r\n[layout] [field|필드셋 타이틀]필드셋[field] [layout]\n[layout] [alert]경고[alert] [layout]\n[layout] [info]안내[info] [layout]");
        var editor = document.getElementById("wikiEditor");		// [object HTMLTextAreaElement]

        // 각 에디터 버튼 클릭시 액션 처리
        $(".sonDualEditor #btnToolbar .btn-group .btn-sm").each(function(){
            $(this).on("click", function() {
                var $me = $(this);
                var data = $me.data();
                // 에디터 액션 처리
                $.editorAction(editor, $me, data);
            });
        });

        // 각 에디터 버튼 클릭시 액션 처리
        $(".sonDualEditor #btnToolbar .btn-group .dropdown-menu a").each(function(){
            $(this).on("click", function() {
                var $me = $(this);
                var data = $me.data();
                // 에디터 액션 처리
                $.editorAction(editor, $me, data);
            });
        });

        setInterval(function() {
            $("#wikimaincol").text("");
            var txt = DualEditor.markup( "<div style=\"width:96%\">"+$("#wikiEditor").val()+"</div>" );
            $("#wikimaincol").append( txt );
        }, 1000);

    };

    DualEditor.getMarkupEditHtml = function(){
        var target = $("#wikiEditor");
        return target.html();
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

function getMarkupEditHtml(width, height){
    var style = "";
    style = width == '' ? style + '' : style + "width:"+width+";";
    style = height == '' ? style + '' : style + "height:"+height+";";
    return "<table class=\"sonDualEditor\" style=\""+style+"\">             <thead>             <tr>                 <th colspan=\"2\">                     <div class=\"btn-toolbar    \" id=\"btnToolbar\">                         <div class=\"btn-group\">                             <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm dropdown-toggle\" data-toggle=\"dropdown\"><i class=\"fa fa-font\"></i><span class=\"caret\"></span></button>                             <ul class=\"dropdown-menu\" role=\"menu\">                                 <li>                                     <a data-mode=\"font\" data-before=\"[font|돋움]\" data-center=\" \" data-after=\" [font]\" unselectable=\"on\">                                         <span style=\"font-family:'돋움', dotum;\" unselectable=\"on\">돋움</span>                                     </a>                                 </li>                                 <li>                                     <a data-mode=\"font\" data-before=\"[font|굴림]\" data-center=\" \" data-after=\" [font]\" unselectable=\"on\">                                         <span style=\"font-family:'굴림', gulim;\" unselectable=\"on\">굴림</span>                                     </a>                                 </li>                                 <li>                                     <a data-mode=\"font\"data-before=\"[font|궁서]\" data-center=\" \" data-after=\" [font]\" unselectable=\"on\">                                         <span style=\"font-family:'궁서', gungsuh;\" unselectable=\"on\">궁서</span>                                     </a>                                 </li>                                 <li>                                     <a data-mode=\"font\"data-before=\"[font|바탕]\" data-center=\" \" data-after=\" [font]\" unselectable=\"on\">                                         <span style=\"font-family:'바탕', batang;\" unselectable=\"on\">바탕</span>                                     </a>                                 </li>                                 <li>                                     <a data-mode=\"font\"data-before=\"[font|Arial]\" data-center=\" \" data-after=\" [font]\" unselectable=\"on\">                                         <span style=\"font-family: Arial; -webkit-user-select: none; \" unselectable=\"on\">Arial</span>                                     </a>                                 </li>                                 <li>                                     <a data-mode=\"font\"data-before=\"[font|Comic Sans MS]\" data-center=\" \" data-after=\" [font]\" unselectable=\"on\">                                         <span style=\"font-family: 'Comic Sans MS'; -webkit-user-select: none; \" unselectable=\"on\">Comic Sans MS</span>                                     </a>                                 </li>                                 <li>                                     <a data-mode=\"font\"data-before=\"[font|Courier New]\" data-center=\" \" data-after=\" [font]\" unselectable=\"on\">                                         <span style=\"font-family: 'Courier New'; -webkit-user-select: none; \" unselectable=\"on\">Courier New</span>                                     </a>                                 </li>                                 <li>                                     <a data-mode=\"font\"data-before=\"[font|Georgia]\" data-center=\" \" data-after=\" [font]\" unselectable=\"on\">                                         <span style=\"font-family: Georgia; -webkit-user-select: none; \" unselectable=\"on\">Georgia</span>                                     </a>                                 </li>                                 <li>                                     <a data-mode=\"font\"data-before=\"[font|Tahoma]\" data-center=\" \" data-after=\" [font]\" unselectable=\"on\">                                         <span style=\"font-family: Tahoma; -webkit-user-select: none; \" unselectable=\"on\">Tahoma</span>                                     </a>                                 </li>                                 <li>                                     <a data-mode=\"font\"data-before=\"[font|Verdana]\" data-center=\" \" data-after=\" [font]\" unselectable=\"on\">                                         <span style=\"font-family: Verdana; -webkit-user-select: none; \" unselectable=\"on\">Verdana</span>                                     </a>                                 </li>                             </ul>                         </div>                         <div class=\"btn-group\">                             <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm dropdown-toggle\" data-toggle=\"dropdown\"><i class=\"fa fa-text-height\"></i><span class=\"caret\"></span></button>                             <ul class=\"dropdown-menu\" role=\"menu\">                                 <li>                                     <a data-mode=\"font\" data-before=\"[size|11]\" data-center=\" \"  data-after=\" [size]\" unselectable=\"on\">                                         <span style=\"font-size:11px;\" unselectable=\"on\">11px</span>                                     </a>                                 </li>                                 <li>                                     <a data-mode=\"font\" data-before=\"[size|12]\" data-center=\" \"  data-after=\" [size]\" unselectable=\"on\">                                         <span style=\"font-size:12px;\" unselectable=\"on\">12px</span>                                     </a>                                 </li>                                 <li>                                     <a data-mode=\"font\" data-before=\"[size|14]\" data-center=\" \"  data-after=\" [size]\" unselectable=\"on\">                                         <span style=\"font-size:14px;\" unselectable=\"on\">14px</span>                                     </a>                                 </li>                                 <li>                                     <a data-mode=\"font\" data-before=\"[size|18]\" data-center=\" \"  data-after=\" [size]\" unselectable=\"on\">                                         <span style=\"font-size:18px;\" unselectable=\"on\">18px</span>                                     </a>                                 </li>                             </ul>                         </div>                         <div class=\"btn-group\">                             <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm dropdown-toggle\" data-toggle=\"dropdown\"><i class=\"fa fa-header\"></i><span class=\"caret\"></span></button>                             <ul class=\"dropdown-menu\" role=\"menu\">                                 <li>                                     <a data-mode=\"font\" data-before=\"# \" data-center=\"\"  data-after=\"\" unselectable=\"on\">                                         <span style=\"font-size:18px;\" unselectable=\"on\"><h1>h1. 큰 헤드라인</h1></span>                                     </a>                                     <a data-mode=\"font\" data-before=\"## \" data-center=\"\"  data-after=\"\" unselectable=\"on\">                                         <span style=\"font-size:14px;\" unselectable=\"on\"><h2>h2. 중간 헤드라인</h2></span>                                     </a>                                     <a data-mode=\"font\" data-before=\"### \" data-center=\"\"  data-after=\"\" unselectable=\"on\">                                         <span style=\"font-size:12px;\" unselectable=\"on\"><h3>h3. 작은 헤드라인</h3></span>                                     </a>                                 </li>                             </ul>                         </div>                         <div class=\"btn-group\">                             <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm dropdown-toggle\" data-toggle=\"dropdown\" data-mode=\"color\" data-before=\"color\" data-center=\" \" data-after=\"color\"><i class=\"fa fa-barcode\"></i><span class=\"caret\"></span></button>                         </div>                         <div class=\"btn-group\">                             <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-mode=\"append\" data-before=\"**\" data-center=\" \" data-after=\"**\"><i class=\"fa fa-bold\"></i></button>                             <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-mode=\"append\" data-before=\"_\" data-center=\" \" data-after=\"_\"><i class=\"fa fa-italic\"></i></button>                             <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-mode=\"append\" data-before=\"//\" data-center=\" \" data-after=\"//\"><i class=\"fa fa-underline\"></i></button>                             <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-mode=\"append\" data-before=\"[d]\" data-center=\" \" data-after=\"[d]\"><i class=\"fa fa-strikethrough\"></i></button>                             <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-mode=\"append\" data-before=\"[sb]\" data-center=\" \" data-after=\"[sb]\"><i class=\"fa fa-subscript\"></i></button>                             <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-mode=\"append\" data-before=\"[sp]\" data-center=\" \" data-after=\"[sp]\"><i class=\"fa fa-superscript\"></i></button>                             <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-mode=\"append\" data-before=\"[align:left]\" data-center=\" \" data-after=\"[align]\"><i class=\"fa fa-align-left\"></i></button>                             <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-mode=\"append\" data-before=\"[align:center]\" data-center=\" \" data-after=\"[align]\"><i class=\"fa fa-align-center\"></i></button>                             <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-mode=\"append\" data-before=\"[align:right]\" data-center=\" \" data-after=\"[align]\"><i class=\"fa fa-align-right\"></i></button>                         </div>                         <div class=\"btn-group\">                             <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-mode=\"insert\" data-before=\"***\" data-center=\"\" data-after=\"\"><i class=\"glyphicon glyphicon-minus\"></i></button>                         </div>                         <div class=\"btn-group\">                         </div>                         <div class=\"btn-group\">                             <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-mode=\"append\" data-before=\"* \" data-center=\" \" data-after=\"\"><i class=\"fa fa-list-ul\"></i></button>                             <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-mode=\"append\" data-before=\"1. \" data-center=\" \" data-after=\"\"><i class=\"fa fa-list-ol\"></i></button>                             <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-mode=\"append\" data-before=\"[syntax]\" data-center=\"\"  data-after=\"[syntax]\"><i class=\"fa fa-code\"></i></button>                             <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-toggle=\"modal\" data-target=\"#tableModal\" data-mode=\"layer\" data-type=\"table\"><i class=\"fa fa-table\"></i></button>                             <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-mode=\"append\" data-before=\"[field|타이틀]\" data-center=\"\"  data-after=\"[field]\"><i class=\"fa fa-credit-card\"></i></button>                             <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-toggle=\"modal\" data-target=\"#urlModal\" data-mode=\"layer\" data-type=\"url\" ><i class=\"fa fa-link\"></i></button>                             <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-toggle=\"modal\" data-target=\"#imgModal\" data-mode=\"layer\" data-type=\"img\" ><i class=\"fa fa-file-image-o\"></i></button>                             <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-mode=\"append\" data-before=\"[alert]\" data-center=\" \" data-after=\"[alert]\"><i class=\"fa fa-exclamation-triangle\"></i></button>                             <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" data-mode=\"append\"  data-before=\"[info]\"  data-center=\" \"  data-after=\"[info]\"><i class=\"fa fa-info\"></i></button>                             <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" ><i class=\"fa fa-lightbulb-o\"></i></button>                         </div>                     </div>                 </th>             </tr>             </thead>             <tbody>             <tr>                 <td style=\"width: 50%; padding-top: 10px;padding-left: 0px;\">                     <textarea class=\"dualEditor form-control\" rows=\"20\" id=\"wikiEditor\" name=\"we_wiki_text\" style=\"overflow: scroll; height: 500px;\"></textarea>                 </td>                 <td style=\"width: 50%; padding-top: 10px;padding-left: 10px;\">                     <div id=\"wikimaincol\" style=\"background-color:white; border:1px solid #ccc; padding-top:5px; padding-left:10px; border-radius:5px; overflow: scroll; height: 500px;\">                     </div>                 </td>             </tr>             </tbody>         </table>";
}

function getMarkupEditMiniHtml(width, height){
    var styleWidth = "";
    var styleHeight = "";
    styleWidth += width == '' ? '' : "width:"+width+";";
    styleHeight += height == '' ? '' : "height:"+height+";";
    return "<div class='dualEditor-nav' role='tabpanel' style=\""+styleWidth+"\"> 	<!-- Nav tabs --> 	<ul class='nav nav-tabs ' role='tablist'> 	  <li role='presentation' class='active'><a href='#write' aria-controls='write' role='tab' data-toggle='tab'>Write</a></li> 	  <li role='presentation'><a href='#preview' aria-controls='preview' role='tab' data-toggle='tab'>Preview</a></li> 	</ul> 	</div> 	 	<!-- Tab panes --> 	<div class='tab-content dualEditor-nav' style=\""+styleWidth+"\"> 	  <div role='tabpanel' class='tab-pane preview' id='preview'> 		<div id='wikimaincol' class='dualEditor-preview' style=\""+styleHeight+"\"></div> 	  </div> 	  <div role='tabpanel' class='tab-pane active' id='write'> 	   	  		<table class='sonDualEditor dualEditor-main' style=\""+styleHeight+"\"> 			<thead> 				<tr><td style='padding: 8px'></td></tr> 				<tr> 					<th> 						<div class='btn-toolbar' id='btnToolbar'> 							<div class='btn-group'> 								<button type='button' class='dualEditor-wiki-btn btn btn-default btn-sm dropdown-toggle' data-toggle='dropdown'><i class='fa fa-font'></i><span class='caret'></span></button> 								<ul class='dropdown-menu' role='menu'> 									<li> 										<a data-mode='font' data-before='[font|돋움]' data-center=' ' data-after=' [font]' unselectable='on'> 											<span style='font-family:'돋움', dotum;' unselectable='on'>돋움</span> 										</a> 									</li> 									<li> 										<a data-mode='font' data-before='[font|굴림]' data-center=' ' data-after=' [font]' unselectable='on'> 											<span style='font-family:'굴림', gulim;' unselectable='on'>굴림</span> 										</a> 									</li> 									<li> 										<a data-mode='font'data-before='[font|궁서]' data-center=' ' data-after=' [font]' unselectable='on'> 											<span style='font-family:'궁서', gungsuh;' unselectable='on'>궁서</span> 										</a> 									</li> 									<li> 										<a data-mode='font'data-before='[font|바탕]' data-center=' ' data-after=' [font]' unselectable='on'> 											<span style='font-family:'바탕', batang;' unselectable='on'>바탕</span> 										</a> 									</li> 									<li> 										<a data-mode='font'data-before='[font|Arial]' data-center=' ' data-after=' [font]' unselectable='on'> 											<span style='font-family: Arial; -webkit-user-select: none; ' unselectable='on'>Arial</span> 										</a> 									</li> 									<li> 										<a data-mode='font'data-before='[font|Comic Sans MS]' data-center=' ' data-after=' [font]' unselectable='on'> 											<span style='font-family: 'Comic Sans MS'; -webkit-user-select: none; ' unselectable='on'>Comic Sans MS</span> 										</a> 									</li> 									<li> 										<a data-mode='font'data-before='[font|Courier New]' data-center=' ' data-after=' [font]' unselectable='on'> 											<span style='font-family: 'Courier New'; -webkit-user-select: none; ' unselectable='on'>Courier New</span> 										</a> 									</li> 									<li> 										<a data-mode='font'data-before='[font|Georgia]' data-center=' ' data-after=' [font]' unselectable='on'> 											<span style='font-family: Georgia; -webkit-user-select: none; ' unselectable='on'>Georgia</span> 										</a> 									</li> 									<li> 										<a data-mode='font'data-before='[font|Tahoma]' data-center=' ' data-after=' [font]' unselectable='on'> 											<span style='font-family: Tahoma; -webkit-user-select: none; ' unselectable='on'>Tahoma</span> 										</a> 									</li> 									<li> 										<a data-mode='font'data-before='[font|Verdana]' data-center=' ' data-after=' [font]' unselectable='on'> 											<span style='font-family: Verdana; -webkit-user-select: none; ' unselectable='on'>Verdana</span> 										</a> 									</li> 								</ul> 							</div> 							<div class='btn-group'> 								<button type='button' class='dualEditor-wiki-btn btn btn-default btn-sm dropdown-toggle' data-toggle='dropdown'><i class='fa fa-text-height'></i><span class='caret'></span></button> 								<ul class='dropdown-menu' role='menu'> 									<li> 										<a data-mode='font' data-before='[size|11]' data-center=' '  data-after=' [size]' unselectable='on'> 											<span style='font-size:11px;' unselectable='on'>11px</span> 										</a> 									</li> 									<li> 										<a data-mode='font' data-before='[size|12]' data-center=' '  data-after=' [size]' unselectable='on'> 											<span style='font-size:12px;' unselectable='on'>12px</span> 										</a> 									</li> 									<li> 										<a data-mode='font' data-before='[size|14]' data-center=' '  data-after=' [size]' unselectable='on'> 											<span style='font-size:14px;' unselectable='on'>14px</span> 										</a> 									</li> 									<li> 										<a data-mode='font' data-before='[size|18]' data-center=' '  data-after=' [size]' unselectable='on'> 											<span style='font-size:18px;' unselectable='on'>18px</span> 										</a> 									</li> 								</ul> 							</div> 							<div class='btn-group'> 								<button type='button' class='dualEditor-wiki-btn btn btn-default btn-sm dropdown-toggle' data-toggle='dropdown'><i class='fa fa-header'></i><span class='caret'></span></button> 								<ul class='dropdown-menu' role='menu'> 									<li> 										<a data-mode='font' data-before='# ' data-center=''  data-after='' unselectable='on'> 											<span style='font-size:18px;' unselectable='on'><h1>h1. 큰 헤드라인</h1></span> 										</a> 										<a data-mode='font' data-before='## ' data-center=''  data-after='' unselectable='on'> 											<span style='font-size:14px;' unselectable='on'><h2>h2. 중간 헤드라인</h2></span> 										</a> 										<a data-mode='font' data-before='### ' data-center=''  data-after='' unselectable='on'> 											<span style='font-size:12px;' unselectable='on'><h3>h3. 작은 헤드라인</h3></span> 										</a> 									</li> 								</ul> 							</div> 							<div class='btn-group'> 								<button type='button' class='dualEditor-wiki-btn btn btn-default btn-sm' data-mode='append' data-before='**' data-center=' ' data-after='**'><i class='fa fa-bold'></i></button> 								<button type='button' class='dualEditor-wiki-btn btn btn-default btn-sm' data-mode='append' data-before='_' data-center=' ' data-after='_'><i class='fa fa-italic'></i></button> 								<button type='button' class='dualEditor-wiki-btn btn btn-default btn-sm' data-mode='append' data-before='//' data-center=' ' data-after='//'><i class='fa fa-underline'></i></button> 								<button type='button' class='dualEditor-wiki-btn btn btn-default btn-sm' data-mode='append' data-before='[d]' data-center=' ' data-after='[d]'><i class='fa fa-strikethrough'></i></button> 							</div> 							<div class='btn-group'> 								<button type='button' class='dualEditor-wiki-btn btn btn-default btn-sm' data-mode='append' data-before='* ' data-center=' ' data-after=''><i class='fa fa-list-ul'></i></button> 								<button type='button' class='dualEditor-wiki-btn btn btn-default btn-sm' data-mode='append' data-before='1. ' data-center=' ' data-after=''><i class='fa fa-list-ol'></i></button> 								<button type='button' class='dualEditor-wiki-btn btn btn-default btn-sm' data-mode='append' data-before='[syntax]' data-center=''  data-after='[syntax]'><i class='fa fa-code'></i></button> 								<button type='button' class='dualEditor-wiki-btn btn btn-default btn-sm' data-toggle='modal' data-target='#tableModal' data-mode='layer' data-type='table'><i class='fa fa-table'></i></button> 								<button type='button' class='dualEditor-wiki-btn btn btn-default btn-sm' data-toggle='modal' data-target='#urlModal' data-mode='layer' data-type='url' ><i class='fa fa-link'></i></button> 								<button type='button' class='dualEditor-wiki-btn btn btn-default btn-sm' data-toggle='modal' data-target='#imgModal' data-mode='layer' data-type='img' ><i class='fa fa-file-image-o'></i></button> 								<button type='button' class='dualEditor-wiki-btn btn btn-default btn-sm' ><i class='fa fa-lightbulb-o'></i></button> 								 							</div> 						</div> 					</th> 				</tr> 				<tr><td style='padding: 8px'></td></tr> 			</thead> 			<tbody> 				<tr> 					<td> 						<textarea class='dualEditor form-control' rows='5' id='wikiEditor' name='we_wiki_text' ></textarea> 					</td> 				</tr> 			</tbody> 		</table> 	  </div> 	</div>";

}