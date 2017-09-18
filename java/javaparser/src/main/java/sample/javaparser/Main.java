package sample.javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.AnnotationMemberDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithModifiers;
import com.github.javaparser.ast.nodeTypes.NodeWithSimpleName;
import com.github.javaparser.ast.nodeTypes.modifiers.NodeWithPrivateModifier;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public String hoge;
    private String fuga;
    public static String piyo;
    private static final int aaa = 10;
    int bbb;
    protected boolean ccc;
    
    private int method() {return 1;}
    private Main() {}
    public Main(int aaa) {}
    private static void mmm() {}
    
    public static void main(String[] args) {
        
        Path source = Paths.get("src/main/java/sample/javaparser/Main.java");
        
        try {
            CompilationUnit unit = JavaParser.parse(source);
            List<ImportDeclaration> removeImport = new ArrayList<>();
            Set<RemoveAnnotation> removeAnnotations = new HashSet<>();
            
            unit.accept(new VoidVisitorAdapter<Void>() {
                
                private Set<String> importNames = new HashSet<>();

                @Override
                public void visit(ImportDeclaration n, Void arg) {
                    if (!n.isAsterisk()) {
                        String packageName = n.getNameAsString();
                        String asteriskName = packageName.replaceAll("\\.[^.]+$", ".*");
                        if (this.importNames.add(asteriskName)) {
                            n.setName(asteriskName);
                            n.setAsterisk(true);
                        } else {
                            removeImport.add(n);
                        }
                    }
                    super.visit(n, arg);
                }

                @Override
                public void visit(MarkerAnnotationExpr n, Void arg) {
                    if (isRemoveTargetAnnotation(n)) {
                        removeAnnotations.add(new RemoveAnnotation(n));
                    }
                    super.visit(n, arg);
                }

                @Override
                public void visit(NormalAnnotationExpr n, Void arg) {
                    if (isRemoveTargetAnnotation(n)) {
                        removeAnnotations.add(new RemoveAnnotation(n));
                    }
                    super.visit(n, arg);
                }

                @Override
                public void visit(SingleMemberAnnotationExpr n, Void arg) {
                    if (isRemoveTargetAnnotation(n)) {
                        removeAnnotations.add(new RemoveAnnotation(n));
                    }
                    super.visit(n, arg);
                }

                @Override
                public void visit(FieldDeclaration n, Void arg) {
                    this.removePrivateModifier(n);
                    super.visit(n, arg);
                }

                @Override
                public void visit(MethodDeclaration n, Void arg) {
                    this.removePrivateModifier(n);
                    this.renameLocalVariables(n);

                    super.visit(n, arg);
                }

                @Override
                public void visit(ConstructorDeclaration n, Void arg) {
                    this.removePrivateModifier(n);
                    super.visit(n, arg);
                }

                @Override
                public void visit(ClassOrInterfaceDeclaration n, Void arg) {
                    if (n.isInnerClass() || n.isNestedType()) {
                        this.removePrivateModifier(n);
                    }
                    super.visit(n, arg);
                }

                @Override
                public void visit(EnumDeclaration n, Void arg) {
                    if (n.isNestedType()) {
                        this.removePrivateModifier(n);
                    }
                    super.visit(n, arg);
                }
                
                @Override
                public void visit(AnnotationDeclaration n, Void arg) {
                    if (n.isNestedType()) {
                        this.removePrivateModifier(n);
                    }
                    super.visit(n, arg);
                }

                private void removePrivateModifier(NodeWithPrivateModifier node) {
                    if (node.isPrivate()) {
                        node.removeModifier(Modifier.PRIVATE);
                    }
                }
                
                private void renameLocalVariables(MethodDeclaration n) {
                    System.out.println("[" + n.getNameAsString() + "]");
                    List<VariableDeclarator> variableDeclarations = new ArrayList<>();

                    n.accept(new VoidVisitorAdapter<Void>() {
                        @Override
                        public void visit(VariableDeclarationExpr n, Void arg) {
                            variableDeclarations.addAll(n.getVariables());
                            super.visit(n, arg);
                        }
                    }, null);
                    
                    List<NameExpr> nameExprs = new ArrayList<>();

                    n.accept(new VoidVisitorAdapter<Void>() {
                        @Override
                        public void visit(NameExpr n, Void arg) {
                            System.out.println(n);
                            nameExprs.add(n);
                            super.visit(n, arg);
                        }
                    }, null);

                    int cnt = 0;
                    Map<String, String> renameMapping = new HashMap<>();

                    for (NameExpr nameExpr : nameExprs) {
                        String name = nameExpr.getNameAsString();
                        String newName;
                        
                        if (renameMapping.containsKey(name)) {
                            newName = renameMapping.get(name);
                        } else {
                            newName = "$" + cnt + "$";
                            
                            if (name.length() <= newName.length()) {
                                continue;
                            }
                            
                            cnt++;
                            renameMapping.put(name, newName);
                        }
                        
                        if (variableDeclarations.stream().anyMatch(vd -> vd.getNameAsString().equals(name))) {
                            nameExpr.setName(newName);
                        }
                    }

                    variableDeclarations.forEach(vd -> {
                        String name = vd.getNameAsString();

                        if (renameMapping.containsKey(name)) {
                            String newName = renameMapping.get(name);
                            vd.setName(newName);
                        }
                    });
                }
            }, null);

            removeImport.forEach(ImportDeclaration::remove);
            removeAnnotations.forEach(RemoveAnnotation::removeAnnotation);

//            System.out.println(unit);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
    
    private static final Set<String> REMOVE_TARGET_ANNOTATION_NAMES;
    static {
        Set<String> set = new HashSet<>();
        set.add("Override");
        set.add("Deprecated");
        set.add("Documented");
        set.add("SuppressWarnings");
        set.add("FunctionalInterface");
        set.add("SafeVarargs");
        REMOVE_TARGET_ANNOTATION_NAMES = Collections.unmodifiableSet(set);
    }
    
    private static boolean isRemoveTargetAnnotation(AnnotationExpr annotation) {
        String name = annotation.getName().asString();
        return REMOVE_TARGET_ANNOTATION_NAMES.contains(name);
    }
    
    private static class RemoveAnnotation {
        private final Node annotation;

        private RemoveAnnotation(Node annotation) {
            this.annotation = annotation;
        }

        private void removeAnnotation() {
            this.annotation.getParentNode().ifPresent(parent -> parent.remove(this.annotation));
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Main main = (Main) o;

        if (bbb != main.bbb) return false;
        if (ccc != main.ccc) return false;
        if (hoge != null ? !hoge.equals(main.hoge) : main.hoge != null) return false;
        return fuga != null ? fuga.equals(main.fuga) : main.fuga == null;
    }

    @Override
    public int hashCode() {
        int result = hoge != null ? hoge.hashCode() : 0;
        result = 31 * result + (fuga != null ? fuga.hashCode() : 0);
        result = 31 * result + bbb;
        result = 31 * result + (ccc ? 1 : 0);
        return result;
    }

    @Deprecated
    public void deprecated() {
        
    }
    
    @SuppressWarnings("hoge fuga")
    public void suppress1() {
        
    }

    private int abcdefg;
    
    @SuppressWarnings(value="hoge fuga")
    @MyAnnotation
    public void suppress2() {
        int a, b, c = 20;
        {
            int abcdefg = this.abcdefg;
            int foo = abcdefg + this.abcdefg;
            int bar = this.abcdefg;

            String str = String.valueOf(abcdefg);
        }
        {
            String.valueOf(abcdefg);
        }
    }
    
    @Documented
    @Target(ElementType.METHOD)
    public @interface MyAnnotation {}
    
    @FunctionalInterface
    public interface MyInterface {
        void method();
    }
    
    @SafeVarargs
    static void m(List<String>... stringLists) {
        Object[] array = stringLists;
        List<Integer> tmpList = Arrays.asList(42);
        array[0] = tmpList; // Semantically invalid, but compiles without warnings
        String s = stringLists[0].get(0); // Oh no, ClassCastException at runtime!
    }
    
    private enum MyEnum {
        FOO,
        BAR,
        ;
        
        private MyEnum() {}
    }
    
    private interface MyInterface2 {}
    private @interface MyAnnotation2 {}
    private static class MyInnerClass {}
    private class MyInnerClass2 {
        private int i;
        private void method() {}
    }
    private int i;
    
}
