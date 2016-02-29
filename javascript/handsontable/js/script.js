var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    data: [
        [null, null, null],
        [   1, null,    3]
    ]
});

var cols = table.countCols();
var rows = table.countRows();

console.log('cols=' + cols + ', rows=' + rows);