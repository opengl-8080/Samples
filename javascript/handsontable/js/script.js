var grid = document.getElementById('grid');

new Handsontable(grid, {
    data: [
        ['a', 'd'],
        ['b', 'c']
    ],
    colHeaders: true,
    columnSorting: {
        column: 0,
        sortColumn: false
    }
});
