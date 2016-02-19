var grid = document.getElementById('grid');

new Handsontable(grid, {
    columns: [
        {
            type: 'autocomplete',
            source: ['hoge', 'fuga', 'piyo'],
            filter: false
        }
    ]
});
