package sample.javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.PrettyPrintVisitor;
import com.github.javaparser.printer.PrettyPrinterConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Main {
    private int i = 10;
    public static Map<String, String> map = new HashMap<>();
    
    static {
        System.out.println("foo");
        System.out.println(String.format("foo", "hoge", "fuga"));
    }
    
    {
        for (int i=0, j=0; i<10 && j < 10; i++, j++) {
            if (i == 0 && j < 4) {
                System.out.println("hoge");
            } else if (j == 3) {
                System.out.println("fuga");
                break;
            } else {
                System.out.println("piyo");
                continue;
            }
            
            j += 20;
            
            if (i == 0) System.out.println("test");
            else if (j==2) System.out.println("fuga");
            else System.out.println("piyo");
        }
        
        int i=0;
        while (i<10) {
            switch (i) {
                case 2:
                    System.out.println("!");
                    break;
                case 3:
                    System.out.println("test");
                    break;
                    default:
                        System.out.println("aaa");
            }
            i++;
        }
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.TYPE})
    @interface MyAnnotation {
        String value() default "a";
        int i();
    }
    
    class Hoge implements Comparable<String> {

        @Override
        public int compareTo(String o) {
            return 0;
        }
    }
    
    Function<String, Integer> f = text -> text.length();
    Consumer<String> c = text -> System.out.println(text);
    Supplier<String> s = () -> {
        try {
            try (InputStream in = this.getClass().getResourceAsStream("hoge")) {
                Method method = this.getClass().getMethod("text", Integer.class);
                
                method.invoke(null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException | IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("hoge");
        }
        return "text";
    };
    
    public static void main(String[] args) throws IOException {
        Path source = Paths.get("src/main/java/sample/javaparser/Main.java");
        CompilationUnit unit = JavaParser.parse(source);

        PrettyPrinterConfiguration conf = new PrettyPrinterConfiguration();
        conf.setIndent("");
        conf.setEndOfLineCharacter("");
        Function<PrettyPrinterConfiguration, PrettyPrintVisitor> prettyPrintVisitorFactory = NoSpacePrintVisitor::new;
        conf.setVisitorFactory(NoSpacePrintVisitor::new);

        System.out.println(unit.toString(conf));
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
