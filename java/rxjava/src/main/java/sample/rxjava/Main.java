package sample.rxjava;

import io.reactivex.processors.UnicastProcessor;

public class Main {
    
    public static void main(String[] args) throws Exception {
        UnicastProcessor<Integer> processor = UnicastProcessor.create();
        
        processor.onNext(1);
        processor.onNext(2);
        
        processor.subscribe(data -> System.out.println("1.data=" + data), e -> System.out.println("1.error=" + e), () -> System.out.println("1.complete"));
        
        processor.onNext(3);
        
        processor.subscribe(data -> System.out.println("2.data=" + data), e -> System.out.println("2.error=" + e), () -> System.out.println("2.complete"));

        processor.onNext(4);
        processor.onComplete();
    }
    
    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
