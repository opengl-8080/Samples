package sample.rxjava;

import io.reactivex.Flowable;

import java.util.concurrent.TimeUnit;

public class Main {
    
    public static void main(String[] args) throws Exception {
        Flowable.interval(500, TimeUnit.MILLISECONDS)
                .take(4, TimeUnit.SECONDS)
                .doOnComplete(() -> System.out.println("complete!"))
                .subscribe(System.out::println);
        
        Thread.sleep(10000);
    }
}
