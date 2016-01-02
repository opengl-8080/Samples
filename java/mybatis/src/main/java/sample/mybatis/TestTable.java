package sample.mybatis;

public class TestTable {
    private int id;
    private String string;
    private Integer number;
    
    public TestTable id(int id) {
        this.id = id;
        return this;
    }
    
    public TestTable string(String string) {
        this.string = string;
        return this;
    }
    
    public TestTable number(int number) {
        this.number = number;
        return this;
    }

    @Override
    public String toString() {
        return "TestTable [id=" + id + ", string=" + string + ", number=" + number + "]";
    }
}
