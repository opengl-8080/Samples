package sample.rxjava;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Observable<String> observable = Observable.create(emitter -> {
            String[] messages = {"Hello, World!", "こんにちは、世界！"};
            
            for (String message : messages) {
                if (emitter.isDisposed()) {
                    return;
                }
                emitter.onNext(message);
            }

            emitter.onComplete();
        });
        
        observable.observeOn(Schedulers.computation())
                .subscribe(new Observer<String>() {
                    
                    @Override
                    public void onSubscribe(Disposable disposable) {
                    }

                    @Override
                    public void onNext(String message) {
                        String threadName = Thread.currentThread().getName();
                        System.out.println(threadName + ": " + message);
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
