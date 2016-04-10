var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    columnSorting: true,
    data: [
        [1, 2, 3],
        [4, 5, 6],
        [7, 8, 9]
    ]
});

table.sort(2, false);
