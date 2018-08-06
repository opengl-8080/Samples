package sample.rxjava;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import org.reactivestreams.Subscription;

public class Main {
    
    public static void main(String[] args) throws Exception {
        Flowable.<Integer>create(emitter -> {
            emitter.setCancellable(() -> {
                System.out.println("canceled!!");
            });
            
            for (int i=0; i<10; i++) {
                emitter.onNext(i);
            }
            
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER)
        .subscribe(new FlowableSubscriber<>() {
            private Subscription subscription;
            
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(1);
                this.subscription = subscription;
            }

            @Override
            public void onNext(Integer i) {
                System.out.println("next=" + i);
                if (i == 8) {
                    subscription.cancel();
                } else {
                    subscription.request(1);
                }
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace(System.err);
            }

            @Override
            public void onComplete() {
                System.out.println("complete");
            }
        });
    }
}
