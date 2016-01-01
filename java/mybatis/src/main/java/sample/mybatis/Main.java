package sample.mybatis;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Main {
    
    public static void main(String[] args) throws Exception {
        try (InputStream in = Main.class.getResourceAsStream("/mybatis-config.xml")) {
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
            
            try (SqlSession session = factory.openSession()) {
                Map<String, Object> param = new HashMap<>();
                param.put("id", 10);
                param.put("value", "hogeeee");
                
                Map<String, Object> result = session.selectOne("sample.mybatis.selectTest", param);
                System.out.println(result);
            }
        }
    }
}
