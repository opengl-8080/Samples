package sample.mybatis;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Main {
    
    public static void main(String[] args) throws Exception {
        try (InputStream in = Main.class.getResourceAsStream("/mybatis-config.xml")) {
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
            
            try (SqlSession session1 = factory.openSession();
                 SqlSession session2 = factory.openSession();) {
                
                TestTable testTable = selectAndPrintln("session1", session1);
                testTable.setValue("update");
                
                selectAndPrintln("session1", session1);
                
                selectAndPrintln("session2", session2);
            }
        }
    }
    
    private static TestTable selectAndPrintln(String tag, SqlSession session) {
        TestTable result = session.selectOne("sample.mybatis.selectTest");
        System.out.printf("<<%s>> %s%n", tag, result);
        return result;
    }
}