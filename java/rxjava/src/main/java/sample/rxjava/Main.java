package sample.rxjava;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class Main {
    
    public static void main(String[] args) throws Exception {
        Flowable.range(1, 100)
                .observeOn(Schedulers.computation())
                .filter(it -> {
                    System.out.println("[" + Thread.currentThread().getName() + "][filter] " + it);
                    return it%2 == 0;
                })
                .observeOn(Schedulers.computation())
                .map(it -> {
                    System.out.println("[" + Thread.currentThread().getName() + "][map] " + it);
                    return it*10;
                })
                .observeOn(Schedulers.computation())
                .subscribe(
                    data -> {
                        System.out.println("[" + Thread.currentThread().getName() + "][onNext] " + data);
                    },
                    error -> error.printStackTrace(System.err),
                    () -> System.out.println("complete")
                );
        
        Thread.sleep(1000);
    }
}
