var grid = document.getElementById('grid');

var table = new Handsontable(grid);

setTimeout(function() {
    table.deselectCell();
}, 2000);
