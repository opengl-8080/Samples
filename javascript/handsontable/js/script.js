var grid = document.getElementById('grid');

var table = new Handsontable(grid);

// 独自イベントの追加
Handsontable.hooks.register('onButtonClick');

// イベントハンドラの登録
Handsontable.hooks.add('onButtonClick', function () {
    console.log('click!!');
});

document.getElementById('button').addEventListener('click', function() {
    // 独自イベントを発火
    Handsontable.hooks.run(table, 'onButtonClick');
});
