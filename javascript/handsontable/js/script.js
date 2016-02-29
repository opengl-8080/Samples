var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    data: [
        [   1, null,    2],
        [null, null, null],
        [null, null,    2],
        [   1, null,    2],
        [null, null, null]
    ]
});

var cols = table.countEmptyCols();
var rows = table.countEmptyRows();

console.log('cols=' + cols + ', rows=' + rows);
