var grid = document.getElementById('grid');

new Handsontable(grid, {
    data: [
        ['<u>underline</u>'],
        ['<a href="http://google.co.jp/">google</a>'],
        ['<button onclick="alert(\'hello\')">click</button>']
    ],
    columns: [
        {
            renderer: 'html'
        }
    ]
});
