package sample.mybatis.springboot;

import java.io.IOException;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main {
    
    public static void main(String[] args) throws IOException {
        try (ConfigurableApplicationContext ctx = SpringApplication.run(Main.class, args)) {
            ctx.getBean(Main.class).method();
        }
    }
    
    @Autowired
    private SqlSessionTemplate sqlSession;
    @Autowired
    private TestTableMapper mapper;
    
    public void method() throws IOException {
        System.out.println("[SqlSessionTemplate]");
        this.sqlSession
            .selectList("sample.mybatis.springboot.TestTableMapper.selectTest")
            .forEach(System.out::println);
        
        System.out.println("[TestTableMapper]");
        this.mapper
            .selectTest()
            .forEach(System.out::println);
    }
}
