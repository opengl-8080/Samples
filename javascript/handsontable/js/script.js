var grid = document.getElementById('grid');

new Handsontable(grid, {
    columns: [
        {
            type: 'checkbox'
        }
    ]
});
