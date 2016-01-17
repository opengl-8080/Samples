var grid = document.getElementById('grid');

new Handsontable(grid, {
    data: [
        ['あいうえお', 'かきくけこ'],
        ['さしすせそ', 'たちつてと']
    ],
    colWidths: function(index) {
        return [100, 200][index];
    }
});
