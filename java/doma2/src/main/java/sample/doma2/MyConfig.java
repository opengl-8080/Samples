package sample.doma2;

import javax.sql.DataSource;

import org.seasar.doma.SingletonConfig;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.dialect.OracleDialect;
import org.seasar.doma.jdbc.tx.LocalTransactionDataSource;
import org.seasar.doma.jdbc.tx.LocalTransactionManager;
import org.seasar.doma.jdbc.tx.TransactionManager;

@SingletonConfig
public class MyConfig implements Config {
    
    private static final MyConfig CONFIG = new MyConfig();
    
    private LocalTransactionDataSource ds;
    private Dialect dialect;
    private TransactionManager transactionManager;
    
    private MyConfig() {
//        this.ds = new LocalTransactionDataSource("jdbc:mysql://localhost/test", "test", "test");
//        this.dialect = new MysqlDialect();
        this.ds = new LocalTransactionDataSource("jdbc:oracle:thin:@localhost:1521:test", "test", "test");
        this.dialect = new OracleDialect();
        this.transactionManager = new LocalTransactionManager(this.ds.getLocalTransaction(this.getJdbcLogger()));
    }
    
    @Override
    public DataSource getDataSource() {
        return this.ds;
    }

    @Override
    public Dialect getDialect() {
        return this.dialect;
    }

    @Override
    public TransactionManager getTransactionManager() {
        return this.transactionManager;
    }

    public static MyConfig singleton() {
        return CONFIG;
    }
}
