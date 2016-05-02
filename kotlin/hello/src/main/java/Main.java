import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Integer> integerList = Arrays.asList(1, 2, 3);
        List<? extends Number> numberList = integerList;

        numberList.forEach(n -> System.out.println(n));
    }
}
