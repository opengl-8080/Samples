import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String... args) throws Exception {
        MarkdownToTsv markdownToTsv = new MarkdownToTsv();

        Path targetDir = Paths.get(args[0]);
        FileSystem fileSystem = targetDir.getFileSystem();
        try (WatchService watchService = fileSystem.newWatchService()) {
            WatchKey key = targetDir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);

            while (key.isValid()) {
                WatchKey poll = watchService.poll(500, TimeUnit.MILLISECONDS);
                if (poll == null) {
                    System.out.print(".");
                    continue;
                }

                if (poll.equals(key)) {
                    for (WatchEvent<?> event : poll.pollEvents()) {
                        Path newFile = (Path)event.context();
                        if (newFile.toFile().getName().equals("source.md")) {
                            List<String> lines = Files.readAllLines(targetDir.resolve(newFile), Charset.forName("MS932"));
                            String tsv = markdownToTsv.toTsv(String.join("\n", lines));
                            List<String> tsvLines = Arrays.asList(tsv.split("\n"));
                            Files.write(targetDir.resolve("parsed.tsv"), tsvLines, Charset.forName("MS932"), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);

                            break;
                        }
                    }
                }

                poll.reset();
            }
        }
    }
}