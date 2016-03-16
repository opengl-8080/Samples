var grid = document.getElementById('grid');

var table = new Handsontable(grid);

table.populateFromArray(0, 1, [
    [1, 2],
    [3, 4]
]);
/*
table.populateFromArray(2, 2, [
    ['a', 'b', 'c'],
    ['d', 'e', 'f'],
    ['g', 'h', 'i']
], 3, 4);
*/