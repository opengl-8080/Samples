var grid = document.getElementById('grid');

new Handsontable(grid, {
    columns: [
        {
            type: 'autocomplete',
            source: ['one', 'two', 'three', 'four', 'five']
        }
    ]
});
