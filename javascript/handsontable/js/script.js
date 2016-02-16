var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    data: [
        [1, 2, 3],
        [1, 2, 3],
        [1, 2, 3]
    ],
    tabMoves: function() {
        console.log(arguments);
        return {
            col: 1,
            row: 0
        };
    }
});
