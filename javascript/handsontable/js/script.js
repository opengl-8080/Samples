var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    data: [
        {
            name: 'sato',
            age: 13
        },
        {
            name: 'suzuki',
            age: 15
        },
        {
            name: 'tanaka',
            age: 18
        }
    ]
});

console.log(table.getDataAtRowProp(0, 'age'));
