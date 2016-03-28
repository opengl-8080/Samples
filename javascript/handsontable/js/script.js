var grid = document.getElementById('grid');

new Handsontable(grid, {
    cells: function(row, cols, prop) {
        return row % 2 === 0 ? {type: 'date'} : {type: 'numeric'};
    }
});
