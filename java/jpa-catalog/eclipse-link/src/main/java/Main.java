import sample.jpa.EntityAlpha;
import sample.jpa.JpaExecutor;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        EntityAlpha entity = new EntityAlpha("eclipse link", Arrays.asList("1", "2", "3"));
        JpaExecutor.execute("select a from EntityAlpha a", entity);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@EclipseLink@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }
}
