package sample.mybatis;

public class MyParameter {
    
    private int ida;
    private String value;
    
    public MyParameter(int id, String value) {
        this.ida = id;
        this.value = value;
    }

    public int getIda() {
        System.out.println("get id");
        return ida;
    }
}
