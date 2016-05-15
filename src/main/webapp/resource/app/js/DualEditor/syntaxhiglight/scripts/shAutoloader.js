/**
 * SyntaxHighlighter
 * http://alexgorbatchev.com/SyntaxHighlighter
 *
 * SyntaxHighlighter is donationware. If you are using it, please donate.
 * http://alexgorbatchev.com/SyntaxHighlighter/donate.html
 *
 * @version
 * 3.0.83 (July 02 2010)
 * 
 * @copyright
 * Copyright (C) 2004-2010 Alex Gorbatchev.
 *
 * @license
 * Dual licensed under the MIT and GPL licenses.
 */
(function(){var h=SyntaxHighlighter;h.autoloader=function(){function n(c,a){for(var d=0;d<c.length;d++)i[c[d]]=a}function o(c){var a=document.createElement("script"),d=false;a.src=c;a.type="text/javascript";a.language="javascript";a.onload=a.onreadystatechange=function(){if(!d&&(!this.readyState||this.readyState=="loaded"||this.readyState=="complete")){d=true;e[c]=true;a:{for(var p in e)if(e[p]==false)break a;j&&SyntaxHighlighter.highlight(k)}a.onload=a.onreadystatechange=null;a.parentNode.removeChild(a)}};document.body.appendChild(a)}var f=arguments,l=h.findElements(),i={},e={},j=false,k=null,b;SyntaxHighlighter.all=function(c){k=c;j=true};for(b=0;b<f.length;b++){var m=f[b].pop?f[b]:f[b].split(/\s+/),g=m.pop();n(m,g)}for(b=0;b<l.length;b++)if(g=i[l[b].params.brush]){e[g]=false;o(g)}}})();
