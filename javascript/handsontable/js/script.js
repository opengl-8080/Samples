var grid = document.getElementById('grid');

new Handsontable(grid, {
    columns: [
        {className: 'foo'},
        {}
    ],
    cell: [
        {col: 1, row: 0, className: 'bar'}
    ]
});
