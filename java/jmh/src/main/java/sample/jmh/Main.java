package sample.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.RunnerException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Main {

    public static void main(String[] args) throws RunnerException, IOException {
//        Options options = new OptionsBuilder().include(Main.class.getSimpleName())
//                .warmupIterations(5)
//                .measurementIterations(5)
//                .forks(2)
//                .build();
//        
//        new Runner(options).run();

        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get("build/test.txt"), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (int i=0; i<10000; i++) {
                long start = System.nanoTime();
                new Main().execute();
                long end = System.nanoTime();
                bw.write(String.format("[%05d] %d%n", i, (end - start)));
            }
        }
    }
    
    @Benchmark
    public void execute() {
        for (int i=0; i<10000; i++) {
            String a = "abc";
            String b = "def";
            
            String c = a + b;
        }
    }
}
