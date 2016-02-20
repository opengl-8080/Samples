var grid = document.getElementById('grid');

new Handsontable(grid, {
    columns: [
        {
            type: 'password',
            hashLength: 10
        }
    ]
});
