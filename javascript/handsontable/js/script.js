var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    data: [
        [1, 2, 3],
        [4, 5, 6],
        [7, 8, 9]
    ]
});

console.table(table.getData());
console.table(table.getData(0, 1, 1, 2));
