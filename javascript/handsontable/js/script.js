var grid = document.getElementById('grid');

new Handsontable(grid, {
    data: [
        ['あいうえお', 'かきくけこ'],
        ['さしすせそ', 'たちつてと']
    ],
    colHeaders: ['列１', '列２']
});
