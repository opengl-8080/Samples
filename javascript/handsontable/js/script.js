var grid = document.getElementById('grid');

new Handsontable(grid, {
    data: [
        [false],
        [true],
        [false]
    ],
    columns: [
        {
            type: 'checkbox',
            label: {
                value: 'チェックボックスやで',
                position: 'before'
            }
        }
    ]
});
