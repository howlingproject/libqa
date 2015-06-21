Handlebars.registerHelper('nl2br', function(text) {
    text = Handlebars.Utils.escapeExpression(text);
    text = text.toString().replace(/(\r\n|\n|\r)/gm, '<br>');
    return new Handlebars.SafeString(text);
});



