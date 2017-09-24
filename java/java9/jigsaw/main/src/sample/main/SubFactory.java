package sample.main;

import sample.sub.Sub;

public class SubFactory {
    public static Sub newSub() {
        return new Sub();
    }
}