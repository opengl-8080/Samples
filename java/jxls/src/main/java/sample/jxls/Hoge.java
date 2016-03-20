package sample.jxls;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Hoge {
    
    public String publicField = "Public Field";
    protected String protectedField = "Protected Field";
    String packageField = "Package Field";
    private String privateField = "Private Field";
    
    public String getPublicProperty() {
        return "Public Property";
    }
    
    protected String getProtectedProperty() {
        return "Protected Property";
    }
    
    String getPackageProperty() {
        return "Package Property";
    }
    
    private String getPrivateProperty() {
        return "Private Property";
    }
    
    public String getNull() {
        return null;
    }
    
    public int getInt() {
        return 10;
    }
    
    public long getLong() {
        return 100;
    }
    
    public float getFloat() {
        return 12.3f;
    }
    
    public double getDouble() {
        return 45.6d;
    }
    
    public boolean getBoolean() {
        return true;
    }
    
    public BigInteger getBigInteger() {
        return new BigInteger("1234");
    }
    
    public BigDecimal getBigDecimal() {
        return new BigDecimal("345.67");
    }
    
    public String getString() {
        return "String Value";
    }
    
    public String getEmptyString() {
        return "";
    }
    
    public Date getDate() {
        return new Date();
    }
    
    public LocalDate getLocalDate() {
        return LocalDate.now();
    }
    
    public String[] getArray() {
        return new String[] {"a", "b", "c"};
    }
    
    public List<String> getList() {
        return Arrays.asList("A", "B", "C");
    }
    
    public Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        map.put("foo", "FOO");
        return map;
    }
    
    public Optional<String> getOptional() {
        return Optional.of("Optional Value");
    }
    
    public String method() {
        return "Method Value";
    }
    
    public Fuga getFuga() {
        return new Fuga();
    }
}
