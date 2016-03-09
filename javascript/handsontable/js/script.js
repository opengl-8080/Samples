var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    data: [
        [1, 2, 3],
        [true, false, true],
        ['hoge', 'fuga', 'piyo']
    ]
});

console.log(table.getDataAtRow(2));
