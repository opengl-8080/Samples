var grid = document.getElementById('grid');

new Handsontable(grid, {
    columns: [
        {
            validator: function(value, callback) {
                console.log(value);
                callback(value === 'hoge');
            }
        }
    ]
});
