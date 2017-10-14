package sample.regexp;

public class Main {

    public static void main(String[] args) {
        String text = "<<abc123>>";

        System.out.println(text.replaceAll("[a-z]+", "$"));
    }
}
