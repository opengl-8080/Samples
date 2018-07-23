package sample.rxjava;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Flowable<String> flowable = Flowable.create(emitter -> {
            String[] messages = {"Hello, World!", "こんにちは、世界！"};
            
            for (String message : messages) {
                if (emitter.isCancelled()) {
                    return;
                }
                emitter.onNext(message);
            }

            emitter.onComplete();
        }, BackpressureStrategy.BUFFER);
        
        flowable.observeOn(Schedulers.computation())
                .subscribe(new Subscriber<>() {
                    
                    private Subscription subscription;
                    
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        this.subscription = subscription;
                        this.subscription.request(1L);
                    }

                    @Override
                    public void onNext(String message) {
                        String threadName = Thread.currentThread().getName();
                        System.out.println(threadName + ": " + message);
                        this.subscription.request(1L);
                    }

                    @Override
                    public void onComplete() {
                        String threadName = Thread.currentThread().getName();
                        System.out.println(threadName + ": 完了しました");
                    }

                    @Override
                    public void onError(Throwable error) {
                        error.printStackTrace(System.err);
                    }
                });
        
        Thread.sleep(500L);
    }
}
