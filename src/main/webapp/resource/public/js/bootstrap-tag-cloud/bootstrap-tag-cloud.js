/* =========================================================
 * bootstrap-tag-cloud.js 
 * http://www.collectivepush.com/plugins/bootstrap/
 * =========================================================
 * Copyright 2012 Collective Push
 *
 *Permission is hereby granted, free of charge, to any person obtaining a copy
 *of this software and associated documentation files (the "Software"), to deal
 *in the Software without restriction, including without limitation the rights
 *to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *copies of the Software, and to permit persons to whom the Software is
 *furnished to do so, subject to the following conditions:

 *The above copyright notice and this permission notice shall be included in
 *all copies or substantial portions of the Software.

 *THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *THE SOFTWARE.
 * ========================================================= */

// Add listener for tag removals
$(document).on('click','.tag-cloud', function removeTag(){
	$(this).remove();
});


// Find out which containers we have on this document and setup proper bindings
$(document).ready(function() {

	if ( $("#tag").length > 0 ) { addTagBindings('#tag'); }

	if ( $("#tag-info").length > 0 ) { addTagBindings('#tag-info');	}

	if ( $("#tag-success").length > 0 ) { addTagBindings('#tag-success'); }

	if ( $("#tag-warning").length > 0 ) { addTagBindings('#tag-warning'); }

	if ( $("#tag-danger").length > 0 ) { addTagBindings('#tag-danger');	}
	
	if ( $("#tag-inverse").length > 0 ) { addTagBindings('#tag-inverse');	}				

});


// Dynamically apply bindings based on the type of tag cloud that was
// detected on the page that includes this .js module
function addTagBindings(id) {

		$(id + ' > button').click(function(){ addTag(id); });

		$(id + ' > input').keyup(function (e) {  if (e.keyCode == 13) { addTag(id); }  });	

}


// Dynamically adjust append code based on what type of tagClass
// need to be applied when the tag element is added to the dom
function addTag(id) {
	
	var Tag = $(id + ' > input').val();
	
	var tagClass = '';

	// Setup our class based on what type of container we have everything inside 
	if (id == '#tag') { tagClass = 'tag-cloud'; }
	if (id == '#tag-info') { tagClass = 'tag-cloud tag-cloud-info'; }
	if (id == '#tag-success') { tagClass = 'tag-cloud tag-cloud-success'; }
	if (id == '#tag-warning') { tagClass = 'tag-cloud tag-cloud-warning'; }
	if (id == '#tag-danger') { tagClass = 'tag-cloud tag-cloud-danger'; }
	if (id == '#tag-inverse') { tagClass = 'tag-cloud tag-cloud-inverse'; }

	// If there is no value in the input field then don't do anything
	if (Tag != '') {

		// Append tag with proper styling into the tag cloud 
		$('<li class="'+tagClass+'">'+Tag+'</li>').appendTo("#tag-cloud");

		// Clear input back to nothing
		$(id + ' > input').val('');		

	}	

}
