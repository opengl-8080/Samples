package sample.mybatis;

import java.io.InputStream;
import java.util.function.Function;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

@Path("/sample")
public class SampleResource {
    
    @GET
    public void main() throws Exception {
        execute(in -> new SqlSessionFactoryBuilder().build(in));
    }
    
    private static void execute(Function<InputStream, SqlSessionFactory> sqlSessionFactorySupplier) throws Exception {
        try (InputStream in = SampleResource.class.getResourceAsStream("/mybatis-config.xml")) {
            SqlSessionFactory factory = sqlSessionFactorySupplier.apply(in);
            
            try (SqlSession session = factory.openSession()) {
                System.out.println(session.selectList("sample.mybatis.selectTest"));
            }
        }
        System.out.println();
    }
}
