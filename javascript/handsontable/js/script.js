var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    data: [
        {name: 'aaa', age: 12},
        {name: 'bbb', age: 15},
        {name: 'ccc', age: 18}
    ]
});

table.selectCellByProp(1, 'age');