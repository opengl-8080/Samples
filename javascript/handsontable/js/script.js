var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    data: [
        [1, 2, 3],
        [1, 2, 3],
        [1, 2, 3]
    ]
});

table.alter('insert_row', 1);
table.alter('insert_col', 2);
