var grid = document.getElementById('grid');

new Handsontable(grid, {
    data: [
        [1, 2, 3, 4, 5],
        [1, 2, 3, 4, 5],
        [1, 2, 3, 4, 5],
        [1, 2, 3, 4, 5]
    ],
    copyColsLimit: 2
});
