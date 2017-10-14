package sample.regexp;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().include(Main.class.getSimpleName())
                .warmupIterations(5)
                .measurementIterations(5)
                .forks(2)
                .build();
        
        new Runner(options).run();
    }
    
    static final int loop = 10000;
    
    @Benchmark
    public void useString() {
        for (int i=0; i<loop; i++) {
            "org.openjdk.jmh.annotations.Benchmark".matches("[a-z]+");
        }
    }
    
    @Benchmark
    public void usePattern() {
        Pattern pattern = Pattern.compile("[a-z]+");

        for (int i=0; i<loop; i++) {
            Matcher matcher = pattern.matcher("org.openjdk.jmh.annotations.Benchmark");
            matcher.matches();
        }
    }
}
