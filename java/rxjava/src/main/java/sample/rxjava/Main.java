package sample.rxjava;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Main {

    private static volatile int counter;
    
    public static void main(String[] args) throws Exception {
        Flowable<Integer> f1 = Flowable.range(1, 10000)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation());
        Flowable<Integer> f2 = Flowable.range(1, 10000)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation());

        Set<String> names = ConcurrentHashMap.newKeySet();

        Flowable.merge(f1, f2)
                .subscribe(
                    data -> {
                        names.add(Thread.currentThread().getName());
                        counter++;
                    },
                    error -> error.printStackTrace(System.err),
                    () -> System.out.println("counter=" + counter)
                );
        
        Thread.sleep(1000);
        System.out.println(names);
    }
}
