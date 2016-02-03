var grid = document.getElementById('grid');

var data = [
    [1, 2, 3],
    [4, 5, 6],
    [7, 8, 9]
];

new Handsontable(grid, {
    data: data,
    rowHeaders: true
});
