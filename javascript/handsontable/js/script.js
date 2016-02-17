var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    data: [
        [1, 2, 3, 4, 5, 6, 7, 8, 9, 0],
        [2, 3, 4],
        [3, 4, 5],
        [4, 5, 6],
        [5, 6, 7],
        [6, 7, 8],
        [7, 8, 9],
        [8, 9, 0],
        [9, 0, 1],
        [0, 1, 2]
    ],
    height: 100
});
