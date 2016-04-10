var grid = document.getElementById('grid');

var table = new Handsontable(grid);

document.getElementById('button').addEventListener('click', function () {
    Handsontable.hooks.run(table, 'editCell');
});

Handsontable.hooks.add('editCell', function () {
    this.selectCell(0, 0)
    var editor = this.getActiveEditor();
    editor.beginEditing();
});
