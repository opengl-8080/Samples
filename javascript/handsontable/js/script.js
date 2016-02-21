var grid = document.getElementById('grid');

new Handsontable(grid, {
    data: [
        ['hoge', 'fuga']
    ],
    columns: [
        {
            readOnly: true
        },
        {
        }
    ]
});
