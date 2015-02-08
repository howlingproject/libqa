// sets variable source to the animalTemplate id in index.html
var source = document.getElementById("animalTemplate").innerHTML;

// Handlebars compiles the above source into a template
var template = Handlebars.compile(source);

// data
var data = {animals: [
    {type: "Dog", sound: "woof"},
    {type: "Cat", sound: "meow"},
    {type: "Cow", sound: "moo"}
]};

// data is passed to above template
var output = template(data);

// HTML element with id "animalList" is set to the output above
document.getElementById("animalList").innerHTML = output;