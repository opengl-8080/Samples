var grid = document.getElementById('grid');

new Handsontable(grid, {
    data: [
        ['あいうえお', 'かきくけこ'],
        ['さしすせそ', 'たちつてと']
    ],
    colHeaders: function(index) {
        return ['列A', '列B'][index];
    }
});
