package sample.stream.parallel;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

public class ForkJoinPoolTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        process();
        new ForkJoinPool(3).submit(ForkJoinPoolTest::process).get();
    }
    
    private static void process() {
        Set<String> threadNames = ConcurrentHashMap.newKeySet();
        long total = IntStream.range(0, 1000000).parallel().reduce(0, (memo, i) -> {
            threadNames.add(Thread.currentThread().getName());
            return memo + i;
        });
        System.out.println("total = " + total);
        threadNames.forEach(System.out::println);
    }
}
