package since9;

import since9.html.AllClassesHtml;
import since9.html.ClassHtml;
import since9.html.MemberBlock;
import since9.model.Module;
import since9.model.Modules;
import since9.model.Package;
import since9.model.Type;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;

public class Main {
    private static final String URL_BASE = "http://download.java.net/java/jdk9/docs/api/"; 
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get("./types.md"), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING))) {
            createTypesMd(writer);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(Paths.get("./members.md"), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING))) {
            createMembersMd(writer);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    
    private static void createMembersMd(PrintWriter writer) {
        System.out.println("search new members...");
        Modules modules = new Modules();

        new AllClassesHtml()
                .classesWithParallel()
                .filter(c -> !c.isSince9Type())
                .forEach(classHtml -> {
                    classHtml
                        .members()
                        .filter(MemberBlock::isSince9)
                        .forEach(memberBlock -> {
                            Module module = modules.getOrInitialize(classHtml.moduleName());
                            Package pkg = module.getOrInitialize(classHtml.packageName());
                            Type type = pkg.getOrInitialize(classHtml.getRelativePath(), classHtml.name(), classHtml.description());
                            type.addNewMember(memberBlock.id(), memberBlock.name(), memberBlock.description());
                        });
                });

        System.out.println("write members...");
        
        modules.each(module -> {
            writer.println("# " + module.name);
            module.each(pkg -> {
                writer.println("## " + pkg.name);
                pkg.each(type -> {
                    writer.println("### " + type.name);
                    String typeUrl = URL_BASE + type.path;
                    type.each(member -> {
                        writer.println("- [" + member.name + "](" + typeUrl + "#" + member.id + ")");
                        writer.println();
                        writer.println("> " + member.description);
                        writer.println();
                    });
                });
            });
        });
    }
    
    private static void createTypesMd(PrintWriter writer) {
        System.out.println("search new types...");
        Modules modules = new Modules();

        new AllClassesHtml()
                .classesWithParallel()
                .filter(ClassHtml::isSince9Type)
                .forEach(classHtml -> {
                    Module module = modules.getOrInitialize(classHtml.moduleName());
                    Package aPackage = module.getOrInitialize(classHtml.packageName());
                    aPackage.addNewType(classHtml.getRelativePath(), classHtml.name(), classHtml.description());
                });

        System.out.println("write types...");
        
        modules.each(module -> {
            writer.println("# " + module.name);
            module.each(pkg -> {
                writer.println("## " + pkg.name);
                pkg.each(type -> {
                    writer.println("### [" + type.name + "](" + URL_BASE + type.path + ")");
                    writer.println("> " + type.description);
                    writer.println();
                });
            });
        });
    }
}
