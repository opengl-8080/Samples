var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    data: [
        ['a', 'b', 'c', 'd'],
        ['e', 'f', 'g', 'h'],
        ['i', 'j', 'k', 'l'],
        ['m', 'n', 'o', 'p'],
    ]
});

table.populateFromArray(0, 1, [
    [1, 2],
    [3, 4]
], 0, 0, 'populateFromArray', 'shift_down');
