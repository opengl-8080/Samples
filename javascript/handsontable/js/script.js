var grid = document.getElementById('grid');

new Handsontable(grid, {
    data: [
        [1, 2, 3],
        [1, 2, 3],
        [1, 2, 3]
    ],
    customBorders: [
        {
            range: {
                from: {col: 0, row: 0},
                to: {col: 1, row: 2}
            },
            top: {
                width: 3,
                color: 'red'
            },
            right: {
                width: 2,
                color: '#1199ff'
            }
        }
    ]
});
