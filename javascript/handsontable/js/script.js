var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    data: [
        [1, 2],
        [1, 2],
        [1, 2]
    ]
});

var count = table.countCols();
console.log('count=' + count);
