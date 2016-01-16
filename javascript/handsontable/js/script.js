var data = [
  {name: {first: '佐藤', last: '一郎'}, age: 25},
  {name: {first: '鈴木', last: '二郎'}, age: 11},
  {name: {first: '田中', last: '三郎'}, age: 21}
];

var grid = document.getElementById('grid');

new Handsontable(grid, {
    data: data,
    column: [
        {data: 'age'},
        {data: 'name.first'},
        {data: 'name.last'}
    ]
});
