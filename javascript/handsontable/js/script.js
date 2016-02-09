var grid = document.getElementById('grid');

var data = [
    [1, 2, 3],
    [2, 3, 4],
    [3, 4, 5]
];

new Handsontable(grid, {
    data: data,
    mergeCells: [
        {row: 0, col: 0, rowspan: 2, colspan: 1},
        {row: 1, col: 1, rowspan: 1, colspan: 2}
    ]
});
