var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    columns: [
        {type: 'text'},
        {type: 'numeric'},
        {type: 'numeric'},
        {type: 'autocomplete'}
    ]
});

console.log(table.getDataType(0, 0));
console.log(table.getDataType(0, 1));
console.log(table.getDataType(0, 2));
console.log(table.getDataType(0, 3));
console.log(table.getDataType(0, 1, 0, 2));
console.log(table.getDataType(0, 0, 0, 3));
