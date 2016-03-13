var grid = document.getElementById('grid');

var src = [
    [1, 2, 3],
    [4, 5, 6],
    [7, 8, 9]
];

var table = new Handsontable(grid, {
    data: src
});

console.table(table.getSourceData() === src);
console.table(table.getData() === src);
