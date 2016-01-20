var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    data: [
        ['a', 'd'],
        ['b', 'c']
    ]
});

var item1 = true;

function switchFlag() {
    item1 = !item1;
}

table.updateSettings({
    contextMenu: {
        items: {
            item1: {
                name: function() {
                    return item1 ? '●item1' : 'item1';
                },
                callback: switchFlag
            },
            item2: {
                name: function() {
                    return !item1 ? '●item2' : 'item2';
                },
                callback: switchFlag
            }
        }
    }
});
