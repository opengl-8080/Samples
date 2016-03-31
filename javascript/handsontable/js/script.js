var grid = document.getElementById('grid');

new Handsontable(grid, {
    columns: [
        {
            editor: 'select',
            selectOptions: ['hoge', 'fuga', 'piyo']
        }
    ]
});
