var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    data: [
        [1, 2],
        [1, 2],
        [1, 2]
    ]
});

var cols = table.countCols();
var rows = table.countRows();

console.log('cols=' + cols + ', rows=' + rows);
