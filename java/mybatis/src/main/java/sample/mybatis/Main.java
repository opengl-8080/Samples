package sample.mybatis;

import java.io.InputStream;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Main {
    
    public static void main(String[] args) throws Exception {
        try (InputStream in = Main.class.getResourceAsStream("/mybatis-config.xml")) {
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
            
            try (SqlSession session = factory.openSession(ExecutorType.BATCH)) {
                for (int i=0; i<3; i++) {
                    TestTable t = new TestTable("value-" + i);
                    int updateCount = session.insert("sample.mybatis.insertTest", t);
                    System.out.println("updateCount = " + updateCount);
                }
                
                session.commit();
            }
        }
    }
}