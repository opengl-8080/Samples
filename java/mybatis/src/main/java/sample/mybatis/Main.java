package sample.mybatis;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Main {
    
    public static void main(String[] args) throws Exception {
        try (InputStream in = Main.class.getResourceAsStream("/mybatis-config.xml")) {
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
            
            try (SqlSession session = factory.openSession()) {
                System.out.println("@selectList()");
                List<Foo> result = session.selectList("sample.mybatis.selectFoo");
                
                System.out.println("@result.get(0)");
                Foo foo = result.get(0);
                
                System.out.println("** foo.class = " + foo.getClass() + " **");
                
                System.out.println("@foo.bar 1");
                System.out.println("<< foo.bar = " + foo.bar + " >>");

                System.out.println("@foo.method()");
                foo.method();

                System.out.println("@foo.bar 2");
                System.out.println("<< foo.bar = " + foo.bar + " >>");
            }
        }
    }
}