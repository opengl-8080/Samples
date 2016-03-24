var grid = document.getElementById('grid');


var table = new Handsontable(grid, {
    data: [
        ['foo', 'hoge', 'bar']
    ],
    columns: [
        {type: 'numeric', data: 0},
        {type: 'text', data: 1},
        {type: 'date', data: 2},
    ]
});

document.getElementById('button').addEventListener('click', function() {
    table.validateCells();
});