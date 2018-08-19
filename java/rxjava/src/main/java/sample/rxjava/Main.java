package sample.rxjava;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Main {
    
    public static void main(String[] args) throws Exception {
        Flowable.concatArrayEager(
            Flowable.create(emitter -> {
                IntStream.range(0, 5).forEach(i -> {
                    emitter.onNext(i);
                    sleep(500);
                });
                emitter.onComplete();
            }, BackpressureStrategy.BUFFER),
            Flowable.create(emitter -> {
                IntStream.range(10, 15).forEach(i -> {
                    emitter.onNext(i);
                    sleep(10);
                });
                emitter.onComplete();
            }, BackpressureStrategy.BUFFER)
        ) 
        .subscribe(i -> {
            System.out.println(i);
        });
        
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
