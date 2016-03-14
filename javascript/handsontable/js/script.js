var grid = document.getElementById('grid');

var table = new Handsontable(grid);

table.selectCell(0, 1, 2, 2);

var selected = table.getSelected();

console.log(selected);
