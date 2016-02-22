var grid = document.getElementById('grid');

new Handsontable(grid, {
    comments: true,
    cell: [
        {col: 1, row: 1, comment: 'Hello Comment'}
    ]
});
