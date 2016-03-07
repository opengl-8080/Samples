var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    colWidths: [100, 120],
    rowHeights: [20, 80]
});

console.log(table.getColWidth(0) + ', ' + table.getRowHeight(0));
console.log(table.getColWidth(1) + ', ' + table.getRowHeight(1));
