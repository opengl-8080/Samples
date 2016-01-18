var grid = document.getElementById('grid');

new Handsontable(grid, {
    data: [
        ['a', 'd'],
        ['b', 'c']
    ],
    comments: true,
    customBorders: true,
    contextMenu: ['row_below', '---------', 'col_left', 'col_right', 'remove_row', 'remove_col', 'undo', 'redo', 'make_read_only', 'alignment', 'borders', 'commentsAddEdit', 'commentsRemove']
});
