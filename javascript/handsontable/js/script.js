var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    dataSchema: {
        hoge: null,
        fuga: null,
        piyo: null
    },
    columns: [
        {data: 'hoge'},
        {data: 'fuga'},
        {data: 'piyo'}
    ]
});

table.setDataAtRowProp(2, 'fuga', 'FUGA');
