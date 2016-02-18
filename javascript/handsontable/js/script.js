var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    data: [
        ['あいうえお', '', ''],
        ['', '', ''],
        ['', '', '']
    ],
    colWidths: 50,
    wordWrap: false
});
