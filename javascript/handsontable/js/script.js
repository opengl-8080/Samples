var grid = document.getElementById('grid');

var table = new Handsontable(grid);

table.setCellMetaObject(1, 1, {
    type: 'autocomplete',
    source: ['one', 'two', 'three']
});
