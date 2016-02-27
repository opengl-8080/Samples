var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    data: [
        [1, 2, 3],
        [1, 2, 3],
        [1, 2, 3]
    ]
});

table.clear();
