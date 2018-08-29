package sample.rxjava;

import io.reactivex.Flowable;

public class Main {
    
    public static void main(String[] args) throws Exception {
        Flowable.just(1, 2, 3, 4, 5)
                .repeatWhen(handler -> {
                    System.out.println("handler.class=" + handler.getClass());
                    return handler
                            .doOnNext(data -> System.out.println("handler.next=" + data))
                            .doOnComplete(() -> System.out.println("handler.complete"))
                            .doOnSubscribe(data -> System.out.println("handler.subscribe=" + data))
                            .take(3);
                })
                .doOnNext(data -> System.out.println("next=" + data))
                .doOnComplete(() -> System.out.println("complete"))
                .subscribe(data -> System.out.println("subscribe=" + data));
    }
    
    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
