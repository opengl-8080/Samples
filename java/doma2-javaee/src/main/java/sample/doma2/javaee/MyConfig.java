package sample.doma2.javaee;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;

import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.dialect.Db2Dialect;
import org.seasar.doma.jdbc.dialect.Dialect;

@ApplicationScoped
public class MyConfig implements Config {
    
    @Resource(lookup="java:app/database")
    private DataSource ds;
    private Dialect dialect;
    
    @PostConstruct
    public void setupDialect() {
        this.dialect = new Db2Dialect();
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
