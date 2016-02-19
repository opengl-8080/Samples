var grid = document.getElementById('grid');

new Handsontable(grid, {
    columns: [
        {
            type: 'autocomplete',
            source: function(query, process) {
                console.log('query=' + query);
                process(['hoge', 'fuga', 'piyo']);
            }
        }
    ]
});
