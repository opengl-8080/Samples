package sample.mybatis;

import java.util.List;

public class Foo {
    private int id;
    private List<Bar> barList;
    
    @Override
    public String toString() {
        return "Foo [id=" + id + ", barList=" + barList + "]";
    }
}
