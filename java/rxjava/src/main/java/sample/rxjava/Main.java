package sample.rxjava;

import io.reactivex.Flowable;

import java.util.Arrays;
import java.util.stream.Stream;

public class Main {
    
    public static void main(String[] args) throws Exception {
        Flowable.just(1, 2, 3, 4, 5)
                .doOnNext(i -> {
                    System.out.println("next = " + i);
                })
                .doOnComplete(() -> System.out.println("complete!!"))
                .contains(3)
                .subscribe(result -> System.out.println("result=" + result));
    }
    
    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
