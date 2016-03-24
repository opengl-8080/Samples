var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    colHeaders: ['one', 'two', 'three', 'four', 'five']
});

console.log(table.getColHeader());
console.log(table.getColHeader(2));
console.log(table.getColHeader(5));
console.log(table.getColHeader(-1));