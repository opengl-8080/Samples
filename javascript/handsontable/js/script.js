var grid = document.getElementById('grid');

new Handsontable(grid, {
    columns: [
        {
            type: 'date',
            datePickerConfig: {
                minDate: new Date()
            }
        }
    ]
});
