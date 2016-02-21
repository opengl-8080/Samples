var grid = document.getElementById('grid');

new Handsontable(grid, {
    comments: true,
    contextMenu: ['commentsAddEdit', 'commentsRemove']
});
