DualEditor.markup.H1 = function(contents){
    contents = contents.replace(/^(<br>#|^#) (.*)/igm, "<h1>$2</h1>");
    contents = contents.replace(/^(<br>##|^##) (.*)/igm, "<h2>$2</h2>");
    contents = contents.replace(/^(<br>###|^###) (.*)/igm, "<h3>$2</h3>");

    return contents;
};