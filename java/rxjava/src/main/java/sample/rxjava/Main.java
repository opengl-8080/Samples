package sample.rxjava;

import io.reactivex.Flowable;

public class Main {

    public static void main(String[] args) {
        Flowable.just(1, 2, 3, 4, 5).subscribe(System.out::println);
    }
}
