(function() 
{
	/**
	 * @description : 사용자의 브라우저를 판별한다.
	 * @param : None
	 * @return : String IE, FF, SC, OP
	 * @example : $.clientBrowser();
	 */
	jQuery.clientBrowser = function()
	{
		var client = "";
		if(navigator.userAgent.toLowerCase().indexOf('msie') > -1){    			// IE
			client = "IE";
	    }else{
			client = "oher";
	    }
		return client;
	};
		
	/**
	 * @description : 화면에 레이어를 생성하여 출력한다. IE는 레이어, 기타는 console.log 를 사용한다. 
	 * @param : msg - 출력할 메시지
	 * @return : None
	 * @example : $.print('출력 메시지');
	 */
	jQuery.print = function(msg){
		var browser = $.clientBrowser();
		if(browser == 'IE') {
			var $output = jQuery("#print-output");
			
			if($output.length === 0) {
				$output = jQuery('<div id="print-output" />').appendTo('body');
			}
			jQuery('<div class="print-output-line" />').html(msg).appendTo($output);
		} else {	
			//console.log(msg);
		}
	};
	
	
})(jQuery);


