package sample.javaparser;

/**
 * Javadoc
 */
public class ToStringSample {
    // line comment

    /**
     * Javadoc
     * @param age age
     * @param name name
     */
    public void method(
       int age,
       String name
    ) {
        /* block comment */
        System.out.println(
            "age=" + age +
            "name=" + name
        );
    }
}
