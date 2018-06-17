package sample.stream.parallel;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class ParallelTest {
    public static void main(String[] args) {
        Set<String> threadNames = ConcurrentHashMap.newKeySet();
        
        ParallelTest test = new ParallelTest();

        List<Integer> list = Arrays.asList(1,2,3,4,5);
        Set<Integer> set = new HashSet<>(list);

        ForkJoinPool forkJoinPool = new ForkJoinPool(4);

        System.out.println("set print");
        try {
            forkJoinPool.submit(() -> 
                set.parallelStream().forEach(n -> {
                    threadNames.add(Thread.currentThread().getName());
                    test.print(n);
                })
            ).get();
        } catch (Exception e) {
            return;
        }
        threadNames.forEach(System.out::println);
        threadNames.clear();

        System.out.println("\n\nlist print");
        try {
            forkJoinPool.submit(() -> {
                list.parallelStream().forEach(n -> {
                    threadNames.add(Thread.currentThread().getName());
                    test.print(n);
                });
            }).get();
        } catch (Exception e) {
            return;
        }
        threadNames.forEach(System.out::println);
        threadNames.clear();
    }

    private void print(int i){
        System.out.println("start: " + i);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
        }
        System.out.println("end: " + i);
    }
}

