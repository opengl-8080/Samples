package sample;

import hoge.Hoge;
import hoge.Hoge2;

public class Main {
    public static void main(String[] args) {
        new Hoge().method();
        Hoge2 hoge2 = new Hoge2();
        System.out.println("hoge2=" + hoge2);
    }
}