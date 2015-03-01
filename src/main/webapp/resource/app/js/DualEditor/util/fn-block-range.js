(function() 
{
	jQuery.offsetToRangeCharacterMove = function(textEditor, offset) {
	    return offset - (textEditor.value.slice(0, offset).split("\r\n").length - 1);
	};


	jQuery.adjustOffsets = function(textEditor, start, end) {
	    if (start < 0) {
	        start += textEditor.value.length;
	    }
	    if (typeof end == "undefined") {
	        end = start;
	    }
	    if (end < 0) {
	        end += textEditor.value.length;
	    }
	    return { start: start, end: end };
	};
	
	/**
	 * @description : textarea 의 블럭 시작점과 종료점, 그 사이의 문자열을 리턴한다. 
	 * @param : textEditor( object - textarea ID )
	 * @return : JSON 
	 * @example : 
	 * 
	 * var editor = document.getElementById("textArea ID");
	 * var sel = $.textLocation(editor);
	 * var start = sel.start;
	 * var end = sel.end;
	 * var text = sel.text;
	 */
	jQuery.textLocation = function(textEditor){
		var start = 0;					// 시작점
		var end = 0;					// 종료점 
		var normalizedValue = '';		// 특수문자를 처리한 일반 텍스트 
		var range = '';
		var textInputRange = '';
		var len = '';
		var endRange = '';
		var targetText = '';
		var textLength = 0;
		if(document.selection) {  // IE
			range = document.selection.createRange();
	        if (range && range.parentElement() == textEditor) {
	            len = textEditor.value.length;
	            normalizedValue = textEditor.value.replace(/\r\n/g, "\n");		// 개행문자 치환 후 일반문자 
	            $.print('normalizedValue : ' + normalizedValue);
	            
	            textInputRange = textEditor.createTextRange();           
	            textInputRange.moveToBookmark(range.getBookmark());
	            
	            endRange = textEditor.createTextRange();
	            endRange.collapse(false);
	
	            // 라인피드가 있을 경우 선택된 text의 길이는 IE에서 +1 이 된다. 
	            if (textInputRange.compareEndPoints("StartToEnd", endRange) > -1) {
	                start = end = len;
	            } else {
	                start = -textInputRange.moveStart("character", -len);
	                start += normalizedValue.slice(0, start).split("\n").length - 1;
	
	                if (textInputRange.compareEndPoints("EndToEnd", endRange) > -1) {
	                    end = len;
	                } else {
	                    end = -textInputRange.moveEnd("character", -len);
	                    end += normalizedValue.slice(0, end).split("\n").length - 1;
	                }
	            }
	            targetText = textEditor.value.substring(start, end);
	            textLength : targetText.length;
	        }
		} else {
			len =  textEditor.value.length;
			start = textEditor.selectionStart;
			end = textEditor.selectionEnd; 
			targetText = textEditor.value.substring(start, end);
	    }
		return {
			start : start,
			end : end,
			text : targetText,
			textLength : targetText.length
		};
	};
	
	
   	/**
	 * @description : 현재 위키에 지정된 text를 삽입한다 
	 * @param : textEditor( object - textarea ID )
	 * @param : before - 삽입 태그    
	 * @param : center - 중간 text    
	 * @param : after - 종료 태그    
	 * @return : None
	 * @example : 
	 * 
	 * var editor = document.getElementById("markupEditor");
	 * var sel = $.textLocation(editor);
	 * $.textInsert(textEditor, data.before, data.center, data.after);
	 */
	jQuery.textInsert = function(textEditor, before, center, after) {
		var textValue = before + center + after;
		
        if (document.selection) { //IE
            textEditor.focus();
            var sel = document.selection.createRange();
            sel.text = textValue;
            return;
        } else if (textEditor.selectionStart || textEditor.selectionStart == '0') { // FF, CROME
            var startPos = textEditor.selectionStart;
            var endPos = textEditor.selectionEnd;
            textEditor.value = textEditor.value.substring(0, startPos) + textValue + textEditor.value.substring(endPos, textEditor.value.length);
            textEditor.focus();
            textEditor.selectionStart = startPos + textValue.length;
            textEditor.selectionEnd = startPos + textValue.length;
        }
        else {
            textEditor.value += textArea.value;
            textEditor.focus();
        }
	};

	/**
	 * @description : 드래그로 지정한 영역을 유지한다. IE 와 다른 브라우저를 구분하여 처리 
	 * @param : textEditor( object - textarea ID )
	 * @param : start - 드래그 시작점   
	 * @param : end - 드래그 종료점   
	 * @return : None
	 * @example : 
	 * 
	 * var editor = document.getElementById("markupEditor");
	 * var sel = $.textLocation(editor);
	 * $.rangeTag(editor, 시작지점, 종료지점);
	 */
	jQuery.rangeTag = function(textEditor, start, end){
		
	    if (textEditor.setSelectionRange) {		// FF, CROME
	        textEditor.setSelectionRange(start, end);			
	    } else {  	
	        if(textEditor.createTextRange) {	// IE
	        	textEditor.focus();
	        	
	        	var offsets = $.adjustOffsets(textEditor, start, end);
                var range = textEditor.createTextRange();
                var startCharMove = $.offsetToRangeCharacterMove(textEditor, offsets.start);
                range.collapse(true);
                if (offsets.start == offsets.end) {
                    range.move("character", startCharMove);
                } else {
                    range.moveEnd("character", $.offsetToRangeCharacterMove(textEditor, offsets.end));
                    range.moveStart("character", startCharMove);
                }
                range.select();
	        } 
	    }
	};
	
	/**
	 * @description : 블럭 영역안의 글자를 지정된 문자열로 바꾼다. 
	 * @param : textEditor( object - textarea ID )
	 * @param : text - 대체할 문자열  
	 * @return : None
	 * @example : 
	 * 
	 * var editor = document.getElementById("markupEditor");
	 * $.replaceTag(editor, "대체할 문자열");
	 */
	jQuery.replaceTag = function(textEditor, text){
	    var sel = $.textLocation(textEditor);
	    var val = textEditor.value;
	    var end = text.length - sel.textLength;	// 대체 문자열 길이 만큼 블럭을 재지정해야 한다. 
	  
	    textEditor.value = val.slice(0, sel.start) + text + val.slice(sel.end);
	    $.rangeTag(textEditor, sel.start, sel.end + end); 
	};

	/**
	 * @description : 블럭 대상의 시작점과 종료점에 지정된 태그를 감싼다. 
	 * @param : textEditor( object - textarea ID )
	 * @param : before - 시작점 
	 * @param : after - 종료점   
	 * @param : text - 감싸야 할 대상 문자열     
	 * @return : None
	 * @example : 
	 * 
	 * var editor = document.getElementById("markupEditor");
	 * $.appendTag(editor, $(this), $(this).data());
	 */
	jQuery.appendTag = function(textEditor, $me, data) {
        var sel = $.textLocation(textEditor);
        var val = textEditor.value;
        
        $.print("sel.start : " + sel.start);
        $.print("sel.end : " + sel.end);
        $.print("sel.text : " + sel.text);
        
        // 블럭이 없는 경우 현재 커서에서 태그를 삽입 한다. 
        if(sel.start == 0 && sel.end == 0) {
        	$.textInsert(textEditor, data.before, data.center, data.after);
        } else {
        	textEditor.value = val.slice(0, sel.start) + data.before + sel.text + data.after + val.slice(sel.end);
            
            var startIndex = sel.start + data.before.length;		// 블럭 시작 지점 
            var endIndex = startIndex + sel.text.length;	// 블럭 종료 지점 
            $.rangeTag(textEditor, startIndex, endIndex);
        }
    };
    
    
    /**
	 * @description : 현재 커서에 태그를 삽입한다.  
	 * @param : textEditor( object - textarea ID )
	 * @param : $me - 현재 클릭한 객체  
	 * @param : data - 클릭한 객체의 value        
	 * @return : None
	 * @example : 
	 * 
	 * var editor = document.getElementById("markupEditor");
	 * $.textInsert(editor, $(this), $(this).data());
	 */
    jQuery.insertTag = function(textEditor, $me, data) { 
    	$.textInsert(textEditor, data.before, "", "");
    };
    
	
})(jQuery);



