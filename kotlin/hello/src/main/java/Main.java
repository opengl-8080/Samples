import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Integer> integerList = Arrays.asList(1, 2, 3);
        List<? extends Number> numberList = integerList;

        numberList.forEach(n -> System.out.println(n));

        List<Integer> intList = new ArrayList<>();
        List<? super Number> stringList = new ArrayList<>();

        stringList.add(new Integer(20));
        stringList.add(new Double(100));
        stringList.add(new Long(100));
        stringList.add(new BigDecimal(200));

        Object s = stringList.get(0);

        hoge(new ArrayList<Object>());
        hoge(new ArrayList<Number>());
//        hoge(new ArrayList<Integer>());
//
//        fuga(new ArrayList<Object>());
        fuga(new ArrayList<Number>());
        fuga(new ArrayList<Integer>());
    }

    public static void hoge(List<? super Number> list) {
        Object object = list.get(0);
        list.add(10);
    }

    public static void fuga(List<? extends Number> list) {
        Number number = list.get(0);
//        list.add(10);
    }
}
