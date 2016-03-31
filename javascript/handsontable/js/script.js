var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    undo: true
});

document.querySelector('#undo').addEventListener('click', function() {
    table.undo();
});

document.querySelector('#redo').addEventListener('click', function() {
    table.redo();
});
