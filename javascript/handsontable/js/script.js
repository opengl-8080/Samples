var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    data: [
        ['one', 'two', 'three'],
        ['four', 'five', 'six'],
        ['seven', 'eight', 'nine']
    ],
    search: true
});

Handsontable.Search.global.setDefaultCallback(function(instance, row, col, data, testResult) {
    console.log(arguments);
    instance.getCellMeta(row, col).isSearchResult = testResult;
});

table.search.query('T');
