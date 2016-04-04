var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    comments: true
});

var comments = table.getPlugin('comments');

comments.setRange({
    from: {row: 1, col: 1}
});
comments.editor.setValue('Commnets Plugin');
comments.saveComment();
