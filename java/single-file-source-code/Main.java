import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Main {
    public static void main(String[] args) throws Exception {
        int n = Integer.parseInt(args[0]);
        System.out.println(n);
        if (10 < n) {
            return;
        }
        /*
        Path thisFile = Paths.get("./Main.java");
        String originalContent = Files.readString(thisFile, StandardCharsets.UTF_8);
        Files.writeString(thisFile, originalContent.replace("Hello World!!", "Hello World!!!"), StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
        */

        Runtime.getRuntime().exec(new String[] {"java", "Main.java", String.valueOf(n + 1)});
    }
}