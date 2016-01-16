var data = [
  {name: '佐藤', age: 25, a: 'A'},
  {name: '鈴木', age: 11, b: 'B'},
  {name: '田中', age: 21}
];

var grid = document.getElementById('grid');

new Handsontable(grid, {data: data});
