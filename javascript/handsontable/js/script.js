var grid = document.getElementById('grid');

var table = new Handsontable(grid);

// 独自のイベントハンドラを登録
Handsontable.hooks.add('onButtonClick', function () {
    console.log('click!!');
});

document.getElementById('button').addEventListener('click', function() {
    // イベントを発火
    Handsontable.hooks.run(table, 'onButtonClick');
});