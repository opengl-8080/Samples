var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    data: [
        [1, 2, 3],
        [4, 5, 6],
        [7, 8, 9]
    ]
});

table.selectCell(1, 2);
console.log(table.getValue());

