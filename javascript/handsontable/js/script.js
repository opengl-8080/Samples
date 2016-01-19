var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    data: [
        ['a', 'd'],
        ['b', 'c']
    ]
});

table.updateSettings({
    contextMenu: {
        items: {
            row_above: {
                name: '行を挿入'
            }
        }
    }
});
