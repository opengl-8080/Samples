package sample.doma2.springboot;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.dialect.MysqlDialect;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

@Configuration
public class MyDomaConfig implements Config {
    private DataSource ds;
    private Dialect dialect;

    @PostConstruct
    public void postConstruct() {
        this.dialect = new MysqlDialect();
        DataSource ds = DataSourceBuilder.create()
                    .driverClassName("com.mysql.jdbc.Driver")
                    .url("jdbc:mysql://localhost/test")
                    .username("test")
                    .password("test")
                    .build();
        
        this.ds = new TransactionAwareDataSourceProxy(ds);
    }
    
    @Bean
    @Override
    public DataSource getDataSource() {
        System.out.println("getDataSource");
        return this.ds;
    }

    @Override
    public Dialect getDialect() {
        System.out.println("getDialect");
        return this.dialect;
    }
}
