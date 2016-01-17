var data = [
  {name: '佐藤', age: 25},
  {name: '鈴木', age: 11},
  {name: '田中', age: 21}
];

var grid = document.getElementById('grid');

var table = new Handsontable(grid, {data: data});

table.loadData([
  {name: '御手洗', age: 31},
  {name: '東海林', age: 22}
]);
