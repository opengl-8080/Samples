var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    data: [
        ['a', 'd'],
        ['b', 'c']
    ]
});

var selected = 1;

table.updateSettings({
    contextMenu: {
        items: [
            {
                key: 'item1',
                name: function() {
                    return selected === 1 ? '●item1' : 'item1';
                },
                callback: function() {
                    selected *= -1;
                }
            },
            {
                key: 'item2',
                name: function() {
                    return selected === -1 ? '●item2' : 'item2';
                },
                callback: function() {
                    selected *= -1;
                }
            }
        ]
    }
});