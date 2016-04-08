var grid = document.getElementById('grid');

var table = new Handsontable(grid);

Handsontable.hooks.register('onButtonClick');

Handsontable.hooks.add('onButtonClick', function () {
    console.log(arguments);
});

document.getElementById('button').addEventListener('click', function() {
    Handsontable.hooks.run(table, 'onButtonClick', 1, 2, 3, 4, 5, 6, 7);
});
