var grid = document.getElementById('grid');

new Handsontable(grid, {
    data: [
        ['a', 'd'],
        ['b', 'c']
    ],
    copyPaste: false
});
