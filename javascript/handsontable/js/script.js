var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    data: [
        [null, null, null],
        [   1, null,    3]
    ]
});

var cols = table.countRenderedCols();
var rows = table.countRenderedRows();

console.log('cols=' + cols + ', rows=' + rows);
