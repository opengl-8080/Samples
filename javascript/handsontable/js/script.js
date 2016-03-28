var grid = document.getElementById('grid');

new Handsontable(grid, {
    data: [
        [
            'text', 'text', 'text',
            1, 2, 3,
            '2015/01/01', '2015/01/01', '2015/01/01',
            'foo', 'bar', 'foo'
        ]
    ],
    allowEmpty: false,
    columns: [
        {type: 'text'},
        {allowEmpty: true, type: 'text'},
        {allowEmpty: false, type: 'text'},
        {type: 'numeric'},
        {allowEmpty: true, type: 'numeric'},
        {allowEmpty: false, type: 'numeric'},
        {type: 'date'},
        {allowEmpty: true, type: 'date'},
        {allowEmpty: false, type: 'date'},
        {type: 'autocomplete', source: ['foo', 'bar']},
        {allowEmpty: true, type: 'autocomplete', source: ['foo', 'bar']},
        {allowEmpty: false, type: 'autocomplete', source: ['foo', 'bar']},
    ]
});
