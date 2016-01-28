var grid = document.getElementById('grid');

new Handsontable(grid, {
    data: [
        [1, 2, 3],
        [1, 2, 3],
        [1, 2, 3]
    ],
    customBorders: true,
    contextMenu: ['borders']
});
