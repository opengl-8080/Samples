var grid = document.getElementById('grid');

var table = new Handsontable(grid);

Handsontable.hooks.add('beforeKeyDown', function (e) {
    if (e.keyCode === Handsontable.helper.KEY_CODES.SPACE) {
        console.log('space!');
    } else if (Handsontable.helper.isKey(e.keyCode, 'ARROW_LEFT|ARROW_RIGHT')) {
        console.log('left or right!!');
    }
});
