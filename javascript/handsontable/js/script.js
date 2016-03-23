var grid = document.getElementById('grid');

var flag = true;

var table = new Handsontable(grid, {
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
