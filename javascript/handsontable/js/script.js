var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    data: [
        ['one', 'two', 'three'],
        ['four', 'five', 'six'],
        ['seven', 'eight', 'nine']
    ],
    search: true
});

table.search.query('T', function(instance, row, col, data, testResult) {
    console.log(arguments);
    instance.getCellMeta(row, col).isSearchResult = testResult;
});
