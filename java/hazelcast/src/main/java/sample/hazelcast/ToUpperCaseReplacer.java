package sample.hazelcast;

import com.hazelcast.config.replacer.spi.ConfigReplacer;

import java.util.Map;
import java.util.Properties;

public class ToUpperCaseReplacer implements ConfigReplacer {
    @Override
    public void init(Properties properties) {
        System.out.println("init()");
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    @Override
    public String getPrefix() {
        return "UPPER";
    }

    @Override
    public String getReplacement(String maskedValue) {
        System.out.println("getReplacement(" + maskedValue + ")");
        return maskedValue.toUpperCase();
    }
}
