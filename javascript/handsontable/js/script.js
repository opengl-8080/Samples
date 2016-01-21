var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    data: [
        ['a', 'd'],
        ['b', 'c']
    ]
});

table.updateSettings({
    contextMenu: {
        items: [
            {
                key: 'myItem',
                name: 'メニュ～',
                callback: function() {
                    alert('クリックした！');
                }
            }
        ]
    }
});