var data = [
  ['佐藤', 28],
  ['鈴木', 19],
  ['田中', 25]
];

var grid = document.getElementById('grid');

new Handsontable(grid, {data: data});
