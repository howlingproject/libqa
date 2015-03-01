(function() 
{
	/**
	 * @description : Editor의 버튼별 Action을 구현한다.  
	 * @param : textEditor( object - textarea ID )
	 * @return :  
	 * @example : 
	 * 
	 * $.editorAction(editor, $me, data);
	 */
    jQuery.editorAction = function(textEditor, $me, data) {
    	$.print("textEditor : " + textEditor);
    	$.print("$me : " + $me);
		$.print("data : " + data);
    	
		if(data.mode == 'append') {
			// 현재 블럭 위치에 지정된 태그를 감싼다. Textarea, btn-object, btn-data 
			$.appendTag(textEditor, $me, data);
		} else if(data.mode == 'insert') {
			// 현재 커서 위치에 지정된 태그를 추가한다.
			$.appendTag(textEditor, $me, data);
		} else if(data.mode == 'font') {
			$.appendTag(textEditor, $me, data);
		} else if(data.mode == 'color') {
			$.layer_select.color.dropdownColor(textEditor, $me, data);
		} else if(data.mode == 'layer') {
			
			if( data.type == 'table' ){
				$.layer_select.table.open(textEditor, $me, data);	
			}else if( data.type == 'url' ){
				$.layer_select.link.open(textEditor, $me, data);
			}else if( data.type == 'img' ){
				$.layer_select.img.open(textEditor, $me, data);
			}
			
			// 현재 커서 위치에 레이어 입력 태그를 추가한다.
			/*
			if (document.selection) { //IE
				textEditor.currentPos = document.selection.createRange().duplicate();	
			}
			var $layer = $.makeLayer.getLayer( data.type );
			$layer({
				'textEditor' : textEditor,
				'tempLayer' : tempLayer
			});
			
			*/
		} else if(data.mode == 'rich') {
			
			if( data.type == "undo" || data.type == "redo" ){
				document.execCommand( data.type, false, null );
			}else{
				$.richEditTag(textEditor, $me, data);	
			}
		}
    };
    
	
})(jQuery);    