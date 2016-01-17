var jisung;
(function() 
{
	
	var COLOR_BOX = {
			colors : [
						'#ffffff', '#000000', '#eeece1', '#1f497d', '#4f81bd', '#c0504d', '#9bbb59', '#8064a2', '#4bacc6', '#f79646', '#ffff00',
						'#f2f2f2', '#7f7f7f', '#ddd9c3', '#c6d9f0', '#dbe5f1', '#f2dcdb', '#ebf1dd', '#e5e0ec', '#dbeef3', '#fdeada', '#fff2ca',
						'#d8d8d8', '#595959', '#c4bd97', '#8db3e2', '#b8cce4', '#e5b9b7', '#d7e3bc', '#ccc1d9', '#b7dde8', '#fbd5b5', '#ffe694',
						'#bfbfbf', '#3f3f3f', '#938953', '#548dd4', '#95b3d7', '#d99694', '#c3d69b', '#b2a2c7', '#b7dde8', '#fac08f', '#f2c314',
						'#a5a5a5', '#262626', '#494429', '#17365d', '#366092', '#953734', '#76923c', '#5f497a', '#92cddc', '#e36c09', '#c09100',
						'#7f7f7f', '#0c0c0c', '#1d1b10', '#0f243e', '#244061', '#632423', '#4f6128', '#3f3151', '#31859b', '#974806', '#7f6000'
					 ]
			
	};	
	
	jQuery.layer_select = function(){};
	/**
	 * 사용자 프로필 레이어 팝업을 출력한다. 
	 */
	jQuery.layer_select.color = {

		dropdownColor : function ( textEditor, $me, data ) {

			this.hideAllDropdownColor();

			var colorItems = $("<div id='dropdownColor' >").addClass('dropdown-menu').css({'width':'210px'});

			var colorLength = COLOR_BOX.colors.length;

			for( var i=0 ; i < colorLength ; i += 1) {
				var color = COLOR_BOX.colors[i];
				var swatch = $('<a rel="' + color + '" href="javascript:void(null);" class="dualEditor_color_link" ></a>').css({ 'backgroundColor': color });

				swatch.on('click', function (e) {
					e.preventDefault();
					var $me = $(this);
					var tagColor = $me.attr('rel').replace('#','');
                    var param = ({
                        'before' : '[' + data.before + '|' + tagColor + ']',
                        'center' : data.center,
                        'after' : '[' + data.after + ']'
                    });
                    $.appendTag( textEditor, $me, param );
                });

                colorItems.append(swatch);

            }

            colorItems.appendTo($me.parent());

        },

        hideAllDropdownColor : function () {
            $('#dropdownColor').remove();
        }
    };

    jQuery.layer_select.table = {

        open : function ( textEditor, $me, data ) {

            this.hideTable();

            var table = $("<div class='modal fade' id='tableModal' data-keyboard='false' role='dialog' aria-labelledby='myModalLabel' aria-hidden='true'>   <div class='modal-dialog'>     <div class='modal-content'>       <div class='modal-body'>         		<form class='form-inline' id='tableHYform' role='form'> 			<div class='form-group'> 				<input type='number' class='form-control'> 행 x 			</div> 			<div class='form-group'> 				<input type='number' class='form-control'> 열 			</div> 			<button type='button' class='btn btn-primary'>적용</button> 		</form>        <hr>                <div id='tableHYdiv'>        </div>               </div>       <div class='modal-footer'>         <button type='button' class='dualEditor-wiki-btn btn btn-default btn-sm' >Close</button>         <button type='button' class='btn btn-primary' id='ok'>Save changes</button>       </div>     </div>   </div> </div>  ");

            table.find(".dualEditor-wiki-btn").on("click", function () {
                $('#tableModal').modal('hide');
            });

            //테이블적용
            table.find('#ok').on("click",function(){
                var html = '';
                html += "||셀제목";
                html += "||셀제목";
                html += "|| \n";

                $(this).parent().parent().find('#tableHYdiv').find('.form-inline').each(function(){
                    $(this).find('input').each(function(){
                        html += "|" + $(this).val();
                        $(this).val("");
                    });
                    html += "| \n"
                });
                jisung = [textEditor, html];
                $.textInsert(textEditor, html, "", "" );
                $('#tableModal').modal('hide');
            });

            //행적용
            table.find('#tableHYform').find("button").on("click", function(){
                var hh = $(this).parent().find("input")[0].value;
                var yy = $(this).parent().find("input")[1].value;
                $(this).parent().parent().find("#tableHYdiv").empty();
                $(this).parent().parent().find("#tableHYdiv").append( $.layer_select.table.tableSetHtml(hh,yy) );
                $.layer_select.table.tableEnvent(this);
            });

            table.appendTo($me.parent());

        },

        tableEnvent : function(item){
            $(item).parent().parent().find("#tableHYdiv form").each(function(){
                $(this).find("button").on("click",function(){
                    $(this).parent().remove();
                });
            });
        },

        tableSetHtml : function(hh, yy){
            var html = "";
            for( var h=0; h < hh; h++ ){
         		 html += "<form class=\"form-inline\" role=\"form\">\n";
         		 for( var y=0; y < yy; y++ ){
         			 html += "<div class=\"form-group\">\n";
         			 html += "<input type=\"text\" class=\"form-control\">\n";
         			 html += "</div>\n";
         		 }
         		 html += "<button type=\"button\" class=\"btn btn-primary\">행삭제</button>\n</form>\n";			 
         	 }
         	return html;
        },
        
        hideTable : function () {
			$('#tableModal').remove();
		}
    };

    jQuery.layer_select.link = {

        open : function ( textEditor, $me, data ) {

        	this.hideLink();
        	
        	var link = $("<div class=\"modal fade\" id=\"urlModal\" data-keyboard='false' role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">   <div class=\"modal-dialog\">     <div class=\"modal-content\">     	<div class=\"modal-body\"> 			<form class=\"form\" role=\"form\"> 				<div class=\"form-group\"> 					<label for=\"exampleInputLinkText\">Link Text</label> 					<input type=\"text\" class=\"form-control\" id=\"linkText\" placeholder=\"Link Text\"> 				</div> 				<div class=\"form-group\"> 					<label for=\"exampleInputURL\">URL</label> 					<input type=\"text\" class=\"form-control\" id=\"linkUrl\" placeholder=\"URL\"> 				</div> 			</form>       </div>       <div class=\"modal-footer\">         <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" >Close</button>         <button type=\"button\" class=\"btn btn-primary\" id=\"ok\">Save changes</button>       </div>     </div>   </div> </div> ");

            link.find(".dualEditor-wiki-btn").on("click", function () {
                $('#urlModal').modal('hide');
            });

        	//여기서부터 링크
        	link.find('#ok').on("click",function(){
        		var text = $(this).parent().parent().find('input')[0].value ;
        		var url  = $(this).parent().parent().find('input')[1].value ;
        		var html = "["+url+"]("+text+")";		
        		
        		$.textInsert(textEditor, html, "", "" );
        		link.modal('hide');
        	});
        	
        	link.appendTo($me.parent());
        },
        
        hideLink : function () {
			$('#urlModal').remove();
		}
    };
    
    jQuery.layer_select.img = {

        open : function ( textEditor, $me, data ) {

        	this.hideImg();

            var img =
                "<div class=\"modal fade\" id=\"imgModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">" +
                "   <div class=\"modal-dialog\">" +
                "     <div class=\"modal-content\">" +
                "     	<div class=\"modal-body\">" +
                "     	    <ul class=\"nav nav-tabs\" role=\"tablist\">" +
                "     	        <li role=\"presentation\" class=\"active\"><a href=\"#imgLink\" data-id=\"imgLink\" role=\"tab\" data-toggle=\"tab\">Home</a></li>" +
                "     	        <li role=\"presentation\"><a href=\"#imgUpload\" data-id=\"imgUpload\" role=\"tab\" data-toggle=\"tab\">Profile</a></li>" +
                "     	    </ul>" +
                " 			<form class=\"form-horizontal\" role=\"form\" id=\"imageUploadForm\">" +
                "               <div class=\"tab-content\">" +
                "                   <div role=\"tabpanel\" class=\"tab-pane dualEditor-image-well active\" id=\"imgLink\">" +
                "                       <div class=\"form-group\">" +
                "                           <label for=\"exampleInputImgText\">Img Text</label>" +
                "                           <input type=\"text\" class=\"form-control\" id=\"ImgText\" placeholder=\"Img Text\">" +
                "                       </div>" +
                "                       <div class=\"form-group\">" +
                "                           <label for=\"exampleInputURL\">URL</label>" +
                "                           <input type=\"text\" class=\"form-control\" id=\"ImgUrl\" placeholder=\"URL\">" +
                "                       </div>" +
                "                   </div>"+
                "                   <div role=\"tabpanel\" class=\"tab-pane dualEditor-image-well\" id=\"imgUpload\">" +
                "                       <div class=\"form-group\">" +
                "                           <input id=\"image_uploadfile\" type=\"file\" name=\"uploadfile\" accept=\"*\" style=\"display:none\">" +
                "                           <label for=\"fileAttachmentInput\" class=\"col-sm-2 control-label\">첨부파일</label>" +
                "                           <div class=\"col-sm-10 col-md-10\">" +
                "                               <div class=\"row\">" +
                "                                   <div class=\"col-sm-9 col-xs-9 col-md-9\">" +
                "                                       <input id=\"fileAttachmentInput\" class=\"form-control\" type=\"text\" />" +
                "                                   </div>" +
                "                                   <div class=\"col-sm-3 col-xs-3 col-md-3\">" +
                "                                       <button class=\"btn btn-info btn-default\" type=\"button\" onclick=\"$('#image_uploadfile').click();\">선택</button>" +
                "                                   </div>" +
                "                               </div>" +
                "                           </div>" +
                "                       </div>" +
                "                   </div>"+
                "               </div>"+
                " 			</form>" +
                "       </div>" +
                "       <div class=\"modal-footer\">" +
                "         <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" >Close</button>" +
                "         <button type=\"button\" class=\"btn btn-primary\" id=\"ok\">Save changes</button>" +
                "       </div>" +
                "     </div>" +
                "   </div>" +
                "</div> ";
            img = $(img);

            img.find(".dualEditor-wiki-btn").on("click", function () {
                $('#imgModal').modal('hide');
            });

            img.find("#image_uploadfile").on("change", function () {
                var uploadFile = $("#image_uploadfile");
                var $imageUploadForm = $("#imageUploadForm");
                $.ajax({
                    url: "/common/uploadFile",
                    type: "POST",
                    data: new FormData($imageUploadForm[0]),
                    enctype: 'multipart/form-data',
                    processData: false,
                    contentType: false,
                    cache: false,
                    success: function (req) {
                        var text = req.data.realName ;
                        var url  = req.data.filePath+"/"+req.data.savedName;
                        var html = "!["+text+"]("+url+")";

                        $.textInsert(textEditor, html, "", "" );
                        img.modal('hide');
                    },
                    error: function () {
                        alert('업로드 중 에러가 발생했습니다. 파일 용량이 허용범위를 초과 했거나 올바르지 않은 파일 입니다.');
                    }
                });
            });

        	//여기서부터 링크
        	img.find('#ok').on("click",function(){
        		var text = $(this).parent().parent().find('input')[0].value ;
        		var url  = $(this).parent().parent().find('input')[1].value ;
        		var html = "!["+text+"]("+url+")";
        		
        		$.textInsert(textEditor, html, "", "" );
        		img.modal('hide');
        	});
        	
        	img.appendTo($me.parent());
        },
        
        hideImg : function () {
			$('#imgModal').remove();
		}
    };

    jQuery.layer_select.layout = {

        open : function ( textEditor, $me, data ) {

            this.hideLayout();
            var div =
                "<div class=\"modal fade\" id=\"layoutModal\" data-keyboard='false' role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">" +
                "   <div class=\"modal-dialog\">" +
                "     <div class=\"modal-content\">" +
                "     	<div class=\"modal-body\">" +
                " 			<form class=\"form\" role=\"form\">" +
                " 				<div class=\"form-group\">" +
                " 					<label for=\"exampleInputLinkText\">생성할 문단수(최대 4개)</label>" +
                " 					<input type='number' class='form-control' id=\"layoutNum\">" +
                " 				</div>" +
                " 			</form>" +
                "       </div>" +
                "       <div class=\"modal-footer\">" +
                "         <button type=\"button\" class=\"dualEditor-wiki-btn btn btn-default btn-sm\" >Close</button>" +
                "         <button type=\"button\" class=\"btn btn-primary\" id=\"ok\">Save changes</button>" +
                "       </div>" +
                "     </div>" +
                "   </div>" +
                " </div> "
            var layout = $(div);



            layout.find(".dualEditor-wiki-btn").on("click", function () {
                $('#layoutModal').modal('hide');
            });

            //여기서부터 링크
            layout.find('#ok').on("click",function(){
                var layoutNum = $(this).parent().parent().find('input')[0].value ;
                if( layoutNum > 4){
                    return alert("4보다 큽니다.");
                }
                var html = "";
                for( var i=0; i < layoutNum; i++){
                    html += "[layout] 문단 "+(i+1)+" [layout]\n";
                }

                $.textInsert(textEditor, html, "", "" );
                layout.modal('hide');
            });

            layout.appendTo($me.parent());
        },

        hideLayout : function () {
            $('#layoutModal').remove();
        }
    };


})(jQuery);
