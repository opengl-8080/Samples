var grid1 = document.getElementById('grid1');
var grid2 = document.getElementById('grid2');

var table1 = new Handsontable(grid1);
var table2 = new Handsontable(grid2);

Handsontable.hooks.add('afterSelection', function() {
    console.log(arguments);
}, table1);
