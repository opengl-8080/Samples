var grid = document.getElementById('grid');

var data = [];

for (var i=0; i<100; i++) {
    data.push([i]);
}

var table = new Handsontable(grid, {
    data: data,
    height: 100,
    width: 100,
    contextMenu: ['row_above']
});

document
    .querySelector('#button')
    .addEventListener('click', function() {
        console.log({
            countRows: table.countRows(),
            sourceRows: table.countSourceRows(),
            visibleRows: table.countVisibleRows(),
            renderedRows: table.countRenderedRows()
        });
    });
