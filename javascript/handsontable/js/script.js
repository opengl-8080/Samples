var grid = document.getElementById('grid');

new Handsontable(grid, {
    data: [
        [true],
        [false],
        [true]
    ],
    columns: [
        {
            type: 'checkbox'
        }
    ]
});
