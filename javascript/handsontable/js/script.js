var grid = document.getElementById('grid');

new Handsontable(grid, {
    data: [
        ['あいうえお', 'かきくけこ'],
        ['さしすせそ', 'たちつてと']
    ],
    colWidths: [200, 50]
});
