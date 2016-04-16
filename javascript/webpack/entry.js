var foo = require('./foo/foo.json');
console.log('typeof foo = ' + (typeof foo));
console.log('foo = ' + foo);

var bar = require('./bar/sub/bar.json');
console.log('typeof bar = ' + (typeof bar));
console.log('bar = ' + bar);
