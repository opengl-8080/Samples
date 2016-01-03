package sample.mybatis;

public class TestTable {
    private int id;
    private String value;
    
    public TestTable(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "TestTable [id=" + id + ", value=" + value + "]";
    }
}
