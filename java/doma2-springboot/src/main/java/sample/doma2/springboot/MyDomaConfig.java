package sample.doma2.springboot;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.dialect.MysqlDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.stereotype.Component;

@Component
public class MyDomaConfig implements Config {
    
    @Autowired
    private DataSource ds;
    private Dialect dialect;

    @Autowired
    public MyDomaConfig(DataSource ds) {
        this.ds = new TransactionAwareDataSourceProxy(ds);
    }
    
    @PostConstruct
    public void createDialect() {
        this.dialect = new MysqlDialect();
    }
    
    @Override
    public DataSource getDataSource() {
        return this.ds;
    }

    @Override
    public Dialect getDialect() {
        return this.dialect;
    }
}
