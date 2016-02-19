var grid = document.getElementById('grid');

new Handsontable(grid, {
    data: [
        ['no'],
        ['yes'],
        ['no']
    ],
    columns: [
        {
            type: 'checkbox',
            checkedTemplate: 'yes',
            uncheckedTemplate: 'no'
        }
    ]
});
