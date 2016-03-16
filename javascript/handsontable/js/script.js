var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    data: [
        [   1,    2, null],
        [   4,    5, null],
        [null, null, null]
    ]
});

console.log(table.isEmptyCol(1));
console.log(table.isEmptyCol(2));
console.log(table.isEmptyRow(1));
console.log(table.isEmptyRow(2));

