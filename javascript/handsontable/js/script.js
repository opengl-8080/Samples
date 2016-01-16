var data = [
  ['佐藤', 28],
  ['鈴木', 19],
  ['田中', 25]
];

var grid = document.getElementById('grid');

var table = new Handsontable(grid, {
    // JSON 文字列にしてから JavaScript オブジェクトに戻す
    data: JSON.parse(JSON.stringify(data))
});

data.push(['山田', 22]);

table.render();
