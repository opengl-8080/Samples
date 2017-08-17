package sample.reladomo;

import com.gs.fw.common.mithra.bulkloader.BulkLoader;
import com.gs.fw.common.mithra.bulkloader.BulkLoaderException;
import com.gs.fw.common.mithra.connectionmanager.SourcelessConnectionManager;
import com.gs.fw.common.mithra.connectionmanager.XAConnectionManager;
import com.gs.fw.common.mithra.databasetype.DatabaseType;
import com.gs.fw.common.mithra.databasetype.GenericDatabaseType;
import com.gs.fw.common.mithra.databasetype.H2DatabaseType;

import java.sql.Connection;
import java.util.TimeZone;

public class MyConnectionManager implements SourcelessConnectionManager {
    private static final String SCHEMA_NAME = "test";
    private static final MyConnectionManager instance = new MyConnectionManager();

    public static MyConnectionManager getInstance() {
        return instance;
    }
    
    private final XAConnectionManager connectionManager;

    private MyConnectionManager() {
        this.connectionManager = this.createConnectionManager();
    }
    
    private XAConnectionManager createConnectionManager() {
        XAConnectionManager manager = new XAConnectionManager();

        manager.setDriverClassName("org.h2.Driver");
        manager.setJdbcConnectionString("jdbc:h2:mem:" + SCHEMA_NAME);
        manager.setJdbcUser("sa");
        manager.setJdbcPassword("");
        
        manager.setInitialSize(1);
        manager.setPoolName("test connection pool");
        manager.setPoolSize(3);
        manager.initialisePool();
        
        return manager;
    }
    
    @Override
    public Connection getConnection() {
        return this.connectionManager.getConnection();
    }

    @Override
    public DatabaseType getDatabaseType() {
        return H2DatabaseType.getInstance();
    }

    @Override
    public TimeZone getDatabaseTimeZone() {
        return TimeZone.getDefault();
    }

    @Override
    public String getDatabaseIdentifier() {
        return SCHEMA_NAME;
    }

    @Override
    public BulkLoader createBulkLoader() throws BulkLoaderException {
        throw new UnsupportedOperationException("bulk loader is not supported.");
    }
}
