package sample.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class Main {

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().include(Main.class.getSimpleName())
                .warmupIterations(5)
                .measurementIterations(5)
                .forks(2)
                .build();
        
        new Runner(options).run();
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
