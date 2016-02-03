var grid = document.getElementById('grid');

var data = [
    [1, 2, 3],
    [1, 2, 3],
    [1, 2, 3]
];

new Handsontable(grid, {
    data: data,
    colHeaders: true,
    manualColumnResize: true
});
