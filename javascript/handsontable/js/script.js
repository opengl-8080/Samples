var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    colHeaders: true,
    colWidths: [100, 200]
});

document.getElementById('button').addEventListener('click', function() {
    table.updateSettings({
        colHeaders: ['one', 'two', 'three', 'four']
    });
});

