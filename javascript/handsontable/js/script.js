var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    data: [
        [1, 2, 3],
        [true, false, true],
        ['hoge', 'fuga', 'piyo']
    ]
});

console.log(table.getDataAtCell(0, 0));
console.log(table.getDataAtCell(1, 1));
console.log(table.getDataAtCell(2, 2));
