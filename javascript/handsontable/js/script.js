var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    data: [
        ['one', 'two', 'three'],
        ['four', 'five', 'six'],
        ['seven', 'eight', 'nine']
    ],
    search: true
});

var result = table.search.query('T');
console.dir(result);
