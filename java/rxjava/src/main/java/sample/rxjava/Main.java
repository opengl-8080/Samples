package sample.rxjava;

import io.reactivex.Flowable;

import java.util.Arrays;
import java.util.stream.Stream;

public class Main {
    
    public static void main(String[] args) throws Exception {
        Flowable.zip(Arrays.asList(
            Flowable.just(1, 2, 3, 4, 5),
            Flowable.just(10, 20, 30, 40, 50)
        ), (Object[] a) -> Stream.of(a).mapToInt(n -> (int)n).sum())
        .subscribe(System.out::println);
        sleep(5000);
    }
    
    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
