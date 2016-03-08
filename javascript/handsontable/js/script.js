var grid = document.getElementById('grid');

var table = new Handsontable(grid);

var td = grid.querySelector('table tr:nth-child(2) td:nth-child(3)');

var coords = table.getCoords(td);

console.log(coords);
