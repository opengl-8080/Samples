var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    data: [
        {name: 'Sato', age: 14, sex: 'male'},
        {name: 'Suzuki', age: 16, sex: 'female'},
        {name: 'Tanaka', age: 18, sex: 'male'}
    ]
});

console.log('age = ' + table.propToCol('age'));
