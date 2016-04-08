var grid = document.getElementById('grid');

var table = new Handsontable(grid);

Handsontable.hooks.once('afterSelection', function() {
    console.log('selection!');
});
