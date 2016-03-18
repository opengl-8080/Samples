var grid = document.getElementById('grid');

var table = new Handsontable(grid);

table.setCellMeta(1, 1, 'type', 'autocomplete');
table.setCellMeta(1, 1, 'source', ['one', 'two', 'three']);
