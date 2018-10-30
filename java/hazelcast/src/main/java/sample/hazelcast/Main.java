package sample.hazelcast;

import com.hazelcast.config.ClasspathXmlConfig;

public class Main {
    
    public static void main(String[] args) throws Exception {
        ClasspathXmlConfig config = new ClasspathXmlConfig("my-hazelcast.xml");
        String password = config.getProperty("password");
        System.out.println("password=" + password);
//        EncryptionReplacer.main("hoge");
    }
}
