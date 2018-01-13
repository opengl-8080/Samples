package sample.watch;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.Watchable;

import static java.nio.file.StandardWatchEventKinds.*;
import static java.nio.file.WatchEvent.*;

public class Main {

    public static void main(String[] args) {
        WatchService watcher;
        try {
            watcher = FileSystems.getDefault().newWatchService();

            Watchable path = Paths.get("./build");
            path.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while (true) {
            WatchKey watchKey;
            try {
                watchKey = watcher.take();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
                return;
            }

            for (WatchEvent<?> event : watchKey.pollEvents()) {
                Kind<?> kind = event.kind();
                Object context = event.context();
                System.out.println("kind=" + kind + ", context=" + context);
            }
            
            if (!watchKey.reset()) {
                System.out.println("WatchKey が無効になりました");
                return;
            }
        }
    }
}
