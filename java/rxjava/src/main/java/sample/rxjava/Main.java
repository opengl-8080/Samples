package sample.rxjava;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscription;

public class Main {
    
    static int i = 0;
    
    public static void main(String[] args) throws Exception {
        
        Flowable.<Integer>generate(emitter -> {
            if (i < 100) {
                Thread.sleep(50);
                emitter.onNext(i);
                i++;
            } else {
                emitter.onComplete();
            }
        })
        .doOnNext(value -> System.out.println("observe : " + value))
        .observeOn(Schedulers.computation(), false, 2)
        .subscribeOn(Schedulers.computation())
        .subscribe(new FlowableSubscriber<>() {
            Subscription subscription;
            int n = 0;
            
            @Override
            public void onSubscribe(Subscription subscription) {
                this.subscription = subscription;
                this.subscription.request(9);
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("subscribe :: " + integer);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.subscription.request(1);
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace(System.err);
            }

            @Override
            public void onComplete() {
                System.out.println("complete!");
            }
        });
        
        Thread.sleep(10000);
    }
}
