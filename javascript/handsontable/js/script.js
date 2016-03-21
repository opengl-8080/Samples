var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    data: [
        [1, 2, 3],
        [4, 5, 6],
        [7, 8, 9]
    ]
});

var ret = table.spliceRow(1, 1, 2, 'X', 'Y', 'Z');
console.log(ret);
