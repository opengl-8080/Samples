var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    data: [
        ['a', 'd'],
        ['b', 'c']
    ]
});

var flag = true;

table.updateSettings({
    contextMenu: {
        items: [
            {
                name: 'item1',
                disabled: true
            },
            {
                name: 'item2',
                disabled: function() {
                    flag = !flag;
                    return flag;
                }
            }
        ]
    }
});