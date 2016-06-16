package sample.web;

public enum Fuga {
    AAA,
    BBB,
    ;

    public static Fuga fromString(String value) {
        System.out.println("Fuga.fromString(" + value + ")");
        return Fuga.AAA;
    }
}
