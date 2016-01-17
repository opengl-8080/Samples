var grid = document.getElementById('grid');

var table = new Handsontable(grid, {data: [
  {name: '佐藤', age: 25},
  {name: '鈴木', age: 11},
  {name: '田中', age: 21}
]});

table.getData().forEach(function(row) {
    console.log(row);
});