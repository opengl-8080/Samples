var grid = document.getElementById('grid');

var table = new Handsontable(grid);

Handsontable.hooks.add('beforeKeyDown', function (e) {
    var editor = this.getActiveEditor();
    
    if (editor.isOpened() && isArrowKey(e.keyCode)) {
        e.stopImmediatePropagation();
    }
});

function isArrowKey(keyCode) {
    return Handsontable.helper.isKey(keyCode, 'ARROW_UP|ARROW_DOWN|ARROW_LEFT|ARROW_RIGHT');
}
