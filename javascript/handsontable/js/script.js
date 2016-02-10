var grid = document.getElementById('grid');

var data = [
    [1, 2, 3],
    [2, 3, 4],
    [3, 4, 5]
];

var table = new Handsontable(grid, {
    data: data,
    minCols: 2,
    minRows: 3
});
