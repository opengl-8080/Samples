package sample.mybatis;

public class TestTable {
    
    private int id;
    private String string;
    private Embedded embedded;
    
    @Override
    public String toString() {
        return "TestTable [id=" + id + ", string=" + string + ", embedded=" + embedded + "]";
    }
}
