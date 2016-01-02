package sample.mybatis;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Main {
    
    public static void main(String[] args) throws Exception {
        try (InputStream in = Main.class.getResourceAsStream("/mybatis-config.xml")) {
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
            
            try (SqlSession session = factory.openSession()) {
                session.update("sample.mybatis.updateTest",
                        new TestTable().id(1).number(555));
                
                session.update("sample.mybatis.updateTest",
                        new TestTable().id(3).string("update").number(999));
                
                session.commit();
            }
        }
    }
}