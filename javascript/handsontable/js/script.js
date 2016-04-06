var grid = document.getElementById('grid');

new Handsontable(grid);

function afterSelection(row, col) {
    console.log('select(' + row + ', ' + col + ')');
}

Handsontable.hooks.add('afterSelection', afterSelection);

document.getElementById('remove').addEventListener('click', function () {
    Handsontable.hooks.remove('afterSelection', afterSelection);
});
