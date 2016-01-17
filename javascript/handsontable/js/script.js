var grid = document.getElementById('grid');

new Handsontable(grid, {
    data: [
        ['あいうえお', 'かきくけこさしすせそ'],
        ['やゆよ', 'たちつてと']
    ],
    autoColumnSize: true
});
