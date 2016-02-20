var grid = document.getElementById('grid');

new Handsontable(grid, {
    columns: [
        {
            type: 'date',
            datePickerConfig: {
                disableDayFn: function(date) {
                    var minDate = new Date(2016, 1, 15);
                    return date < minDate;
                }
            }
        }
    ]
});
