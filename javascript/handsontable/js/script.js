var grid = document.getElementById('grid');

new Handsontable(grid, {
    data: [
        ['<u>underline</u>'],
        ['<a href="http://google.co.jp/">google</a>'],
        ['<button onclick="alert(\'hello\')">click</button>']
    ],
    columns: [
        {
            renderer: function(instance, td, row, col, prop, value, cellProperties) {
                var escaped = Handsontable.helper.stringify(value);
                escaped = strip_tags(escaped, '<u><a>');
                td.innerHTML = escaped;
            }
        }
    ]
});

// original by: Kevin van Zonneveld (http://kevin.vanzonneveld.net)
function strip_tags(input, allowed) {
    var tags = /<\/?([a-z][a-z0-9]*)\b[^>]*>/gi,
        commentsAndPhpTags = /<!--[\s\S]*?-->|<\?(?:php)?[\s\S]*?\?>/gi;

    // making sure the allowed arg is a string containing only tags in lowercase (<a><b><c>)
    allowed = (((allowed || "") + "").toLowerCase().match(/<[a-z][a-z0-9]*>/g) || []).join('');

    return input.replace(commentsAndPhpTags, '').replace(tags, function ($0, $1) {
        return allowed.indexOf('<' + $1.toLowerCase() + '>') > -1 ? $0 : '';
    });
}
