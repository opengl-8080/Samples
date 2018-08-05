package sample.rxjava;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import org.reactivestreams.Publisher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

public class Main {
    
    public static void main(String[] args) throws Exception {
        Callable<BufferedReader> resourceSupplier = () -> Files.newBufferedReader(Paths.get("./build.gradle"));
        Function<BufferedReader, Publisher<String>> sourceSupplier = reader -> {
            return Flowable.create(emitter -> {
                try {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        emitter.onNext(line);
                    }
                    emitter.onComplete();
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }, BackpressureStrategy.BUFFER);
        };
        Consumer<BufferedReader> resourceDisposer = reader -> {
            try {
                System.out.println("close");
                reader.close();
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
        };
        
        Flowable<String> flowable = Flowable.using(resourceSupplier, sourceSupplier, resourceDisposer);

        flowable.subscribe(
            (line) -> System.out.println("[subscribe]=" + line),
            Throwable::printStackTrace,
            () -> System.out.println("complete")
        );
    }
}
