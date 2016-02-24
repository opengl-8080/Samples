var grid = document.getElementById('grid');

new Handsontable(grid, {
    columns: [
        {type: 'numeric'}
    ],
    cell: [
        {col: 0, row: 2, type: 'date'}
    ]
});
