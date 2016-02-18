var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    columns: [
        {
            type: 'numeric',
            format: '0,000.99'
        }
    ]
});
