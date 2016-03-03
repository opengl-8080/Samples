var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    cell: [
        {
            col: 0,
            row: 0,
            type: 'numeric'
        }
    ]
});

console.log(table.getCellMeta(0, 0));
console.log(table.getCellMeta(0, 1));