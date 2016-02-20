var grid = document.getElementById('grid');

new Handsontable(grid, {
    columns: [
        {
            type: 'date',
            dateFormat: 'YYYY-MM-DD'
        }
    ]
});
