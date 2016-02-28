var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    data: [
        [1, null, 2],
        [null, null, null],
        [null, null, null],
        [1, null, 2]
    ]
});

var cols = table.countEmptyCols();
var rows = table.countEmptyRows();

console.log('cols=' + cols + ', rows=' + rows);
