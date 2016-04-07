var grid = document.getElementById('grid');

var table = new Handsontable(grid);

var bucket = Handsontable.hooks.getBucket(table);

console.log(bucket);