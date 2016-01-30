var grid = document.getElementById('grid');

var data = [];
for (var i=0; i<50; i++) {
    var row = [];
    for (var j=0; j<50; j++) {
        row.push(i + j);
    }
    data.push(row);
}

new Handsontable(grid, {
    data: data,
    fixedColumnsLeft: 2,
    fixedRowsTop: 2
});
