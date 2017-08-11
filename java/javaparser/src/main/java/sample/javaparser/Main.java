package sample.javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        Path source = Paths.get("src/main/java/sample/javaparser/Main.java");
        
        try {
            CompilationUnit unit = JavaParser.parse(source);

            unit.getClassByName("Main").ifPresent(mainClass -> {
                // private final String textValue; フィールドを追加
                mainClass.addField(String.class, "textValue", Modifier.PRIVATE, Modifier.FINAL);
                
                // textValue を初期化するようにコンストラクタを追加
                ConstructorDeclaration constructor = mainClass.addConstructor(Modifier.PUBLIC);
                constructor.addParameter(String.class, "textValue"); // コンストラクタの引数追加
                BlockStmt body = constructor.createBody(); // コンストラクタのボディを追加
                body.addStatement("this.textValue = textValue;");
            });

            System.out.println(unit);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
}
