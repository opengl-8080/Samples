var grid = document.getElementById('grid');

new Handsontable(grid, {
    columns: [
        {
            type: 'date',
            defaultDate: '01/01/2016'
        }
    ]
});
