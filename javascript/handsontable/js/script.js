var data = [
    new MyModel('佐藤', 22),
    new MyModel('鈴木', 31),
    new MyModel('田中', 19)
];

var grid = document.getElementById('grid');

new Handsontable(grid, {
    data: data,
    dataSchema: new MyModel(), // ★空のインスタンスを渡す
    columns: [
        {
            // ★セルの値が参照・変更されたときにコールバックされるので、
            //   第二引数に値が渡されたかどうかでモデルから値を取得するか、
            //   値を設定するかを決める。
            data: function(myModel, name) {
                if (name) {
                    myModel.setName(name);
                } else {
                    return myModel.getName();
                }
            }
        },
        {
            data: function(myModel, age) {
                if (age) {
                    myModel.setAge(age);
                } else {
                    return myModel.getAge();
                }
            }
        }
    ]
});

// ボタンをクリックしたら、現在の data の内容を表示する
document.getElementById('button').addEventListener('click', function() {
    data.forEach(function(myModel) {
        console.log(myModel.toString());
    });
});

// 独自クラス
function MyModel(name, age) {
    var _name = name,
        _age = age;

    this.getName = function() {
        return _name;
    };

    this.setName = function(name) {
        _name = name;
    };

    this.getAge = function() {
        return _age;
    };

    this.setAge = function(age) {
        _age = age;
    };

    this.toString = function() {
        return 'MyModel{name=' + _name + ', age=' + _age + '}';
    };
}
