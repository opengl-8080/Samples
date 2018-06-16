package sample.stream.parallel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Random;

public class Main {
    private static final Path path = Paths.get("numbers.txt");
    
    public static void main(String[] args) {
        warnUp(Main::forLoop, Main::parallelStream);

        measureTime("for loop", Main::forLoop);
        measureTime("parallel stream", Main::parallelStream);
    }
    
    private static long forLoop() {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            long total = 0L;
            
            while ((line = reader.readLine()) != null) {
                total += Long.parseLong(line);
            }
            
            return total;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    
    private static long parallelStream() {
        try {
            return Files.lines(path)
                    .parallel()
                    .mapToLong(Long::parseLong)
                    .reduce(0L, (memo, n) -> memo + n);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    
    private static void warnUp(TestCase<?>... testCases) {
        try {
            System.out.println("warm up...");
            for (int i = 0; i < 100; i++) {
                for (TestCase<?> testCase : testCases) {
                    testCase.execute();
                }
                System.out.print("\r" + i);
            }
            System.out.println("\nfinish warm up");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }    
    
    private static <T> void measureTime(String tag, TestCase<T> testCase) {
        long start = System.currentTimeMillis();

        T result = null;
        try {
            result = testCase.execute();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

        long end = System.currentTimeMillis();
        System.out.println("[" + tag + "] result=" + result + ", time=" + (end - start) + "ms");
    }
    
    private interface TestCase<T> {
        T execute() throws Exception;
    }

    private static void createFile() {
        Random random = new Random();
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (int i=0; i<10_000_000; i++) {
                int n = random.nextInt(10_000);
                writer.write(n + "\n");
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
