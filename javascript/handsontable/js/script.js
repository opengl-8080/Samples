var grid = document.getElementById('grid');

var data = [
    [1, 2, 3],
    [2, 3, 4],
    [3, 4, 5]
];

var table = new Handsontable(grid, {
    data: data,
    mergeCells: true
});

table.updateSettings({
    contextMenu: {
        items: {
            mergeCells: {
                name: function() {
                    var sel = this.getSelected();
                    var info = this.mergeCells.mergedCellInfoCollection.getInfo(sel[0], sel[1]);
                    if (info) {
                        return '結合を解除';
                    } else {
                        return 'セルを結合';
                    }
                }
            }
        }
    }
});
