# ModuleLayer の defineModulesWithManyLoaders() と defineModulesWithOneLoader() のち外
- `defineModulesWithManyLoaders()` は、解決されたモジュール１つ１つに対してクラスローダーが作成される
- `defineModulesWithOneLoader()` は、解決されたすべてのモジュールに対して１つのクラスローダーが作成される

クラスローダーが分かれると、どういう違いが出る？
