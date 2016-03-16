var grid = document.getElementById('grid');

var table = new Handsontable(grid);

table.populateFromArray(0, 0, [
    [1, 2, 3],
    [4, 5, 6],
    [7, 8, 9]
], 4, 4);
