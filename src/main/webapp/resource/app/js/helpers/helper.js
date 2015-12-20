Handlebars.registerHelper('xif', function (v1, operator, v2, options) {
    switch (operator) {
        case '==':
            return (v1 == v2) ? options.fn(this) : options.inverse(this);
        case '<':
            return (v1 < v2) ? options.fn(this) : options.inverse(this);
        case '<=':
            return (v1 <= v2) ? options.fn(this) : options.inverse(this);
        case '>':
            return (v1 > v2) ? options.fn(this) : options.inverse(this);
        case '>=':
            return (v1 >= v2) ? options.fn(this) : options.inverse(this);
        case '&&':
            return (v1 && v2) ? options.fn(this) : options.inverse(this);
        case '||':
            return (v1 || v2) ? options.fn(this) : options.inverse(this);
        default:
            return options.inverse(this);
    }
});

Handlebars.registerHelper('length', function(str) {
    if( str.length ) return str.length;
    else return 0;
});

Handlebars.registerHelper('keywordLink', function(keywordType, keywordName) {
    if( keywordType == null ) return '#';

    if( 'WIKI' == keywordType ){
        return '/wiki/list/keyword/'+keywordName;
    }
    return '#';
});

Handlebars.registerHelper('keywordBadge', function(keywordName){
    var keywordLength = Handlebars.helpers.length(keywordName);

    switch (keywordLength) {
        case 1:
            return "<span class=\"label label-primary\">" + keywordName +"</span>";
        case 2:
            return "<span class=\"label label-success\">" + keywordName +"</span>";
        case 3:
            return "<span class=\"label label-info\">" + keywordName +"</span>";
        case 4:
            return "<span class=\"label label-warning\">" + keywordName +"</span>";
        case 5:
            return "<span class=\"label label-danger\">" + keywordName +"</span>";
        case 6:
            return "<span class=\"label label-tag\">" + keywordName +"</span>";
        case 7:
            return "<span class=\"label label-focus\">" + keywordName +"</span>";
        case 8:
            return "<span class=\"label label-alert\">" + keywordName +"</span>";
        default :
            return "<span class=\"label label-default\">" + keywordName +"</span>";
    }
});