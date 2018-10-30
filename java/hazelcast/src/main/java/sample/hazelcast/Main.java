package sample.hazelcast;

import com.hazelcast.config.ClasspathXmlConfig;

public class Main {
    
    public static void main(String[] args) throws Exception {
        ClasspathXmlConfig config = new ClasspathXmlConfig("my-hazelcast.xml");
        System.out.println("hoge=" + config.getProperty("hoge"));
        System.out.println("fuga=" + config.getProperty("fuga"));
    }
}
