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
                key: 'myMenu',
                name: 'my menu',
                submenu: {
                    items: [
                        {
                            key: 'subMenu1',
                            name: 'sub menu 1'
                        },
                        {
                            key: 'subMenu2',
                            name: 'sub menu 2'
                        }
                    ]
                }
            }
        ]
    }
});
