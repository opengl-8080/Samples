var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    comments: true
});

table.add('afterSelection', function() {
    console.log(arguments);
});
