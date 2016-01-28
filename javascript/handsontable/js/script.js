var grid = document.getElementById('grid');

new Handsontable(grid, {
    data: [
        [1, 2, 3],
        [1, 2, 3],
        [1, 2, 3]
    ],
    customBorders: [
        {
            row: 1,
            col: 1,
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
