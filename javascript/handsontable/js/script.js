var grid = document.getElementById('grid');

new Handsontable(grid, {
    data: [
        [1, 2, 3],
        [1, 2, 3],
        [1, 2, 3]
    ],
    enterMoves: {
        row: 1,
        col: 1,
    }
});
