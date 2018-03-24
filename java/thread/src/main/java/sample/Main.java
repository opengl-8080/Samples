package sample;

import org.apache.commons.dbcp2.BasicDataSource;
import org.h2.tools.Server;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * single thread, individual update, standalone:  24467ms
 * single thread,      batch update, standalone:  16270ms
 *  multi thread, individual update, standalone:  23325ms
 *  multi thread,      batch update, standalone:  15499ms
 * single thread, individual update,     server: 649822ms
 * single thread,      batch update,     server: 287157ms
 *  multi thread, individual update,     server: 257172ms
 *  multi thread,      batch update,     server: 144632ms
 */
public class Main {
    private static final boolean singleThread = false;
    private static final boolean batchUpdate = true;
    private static final boolean standalone = false;

    private static final String INSERT_SQL = "insert into logs values (?, ?, ?, ?, ?)";
    
    private static Server server;
    private static DataSource dataSource;
    public static void main(String[] args) throws Exception {
        if (singleThread) {
            System.out.print("single thread, ");
        } else {
            System.out.print(" multi thread, ");
        }

        if (batchUpdate) {
            System.out.print("     batch update, ");
        } else {
            System.out.print("individual update, ");
        }
        
        if (standalone) {
            System.out.print("standalone: ");
            initStandaloneDatabase();
        } else {
            System.out.print("    server: ");
            initServerDatabase();
        }
        
        recreateTable();

        System.out.println();
        stopWatch(() -> {
            if (singleThread) {
                test_single_thread();
            } else {
                test_multi_thread();
            }
        });
        
        if (server != null) {
            server.stop();
        }
    }
    
    private static void initStandaloneDatabase() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:h2:./build/database");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setInitialSize(4);
        dataSource.setMaxTotal(4);
        Main.dataSource = dataSource;
    }
    
    private static void initServerDatabase() throws SQLException {
        server = Server.createTcpServer();
        server.start();
        
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:h2:tcp://localhost/./build/database");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setInitialSize(4);
        dataSource.setMaxTotal(4);
        Main.dataSource = dataSource;
    }
    
    private static void recreateTable() throws SQLException {
        try (Connection con = dataSource.getConnection()) {
            Database.update(con, "drop table logs");
            Database.update(con,"create table logs (" +
                    "  user_name nvarchar2(64) not null," +
                    "  date_time timestamp not null," +
                    "  sequence_number integer not null," +
                    "  ap_name nvarchar2(64) not null," +
                    "  message nvarchar(256) not null" +
                    ")");
        }
    }
    
    /**
     * standalone:  25482ms
     * server:     311727ms
     */
    private static void test_multi_thread() {
        AtomicInteger counter = new AtomicInteger(0);
        List<Callable<Void>> tasks = new ArrayList<>();

        for (int i=0; i<3; i++) {
            String ap = "ap" + i;
            
            tasks.add(() -> {
                try (Database database = new Database(dataSource.getConnection(), INSERT_SQL)) {
                    insertLog(database, ap, counter);
                }
                return null;
            });
        }
        
        ExecutorService service = Executors.newFixedThreadPool(3);
        try {
            service.invokeAll(tasks);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            service.shutdown();
        }
    }
    
    /**
     * standalone:  23692ms
     * server:     542231ms
     */
    private static void test_single_thread() throws SQLException {
        AtomicInteger counter = new AtomicInteger(0);
        try (Database database = new Database(dataSource.getConnection(), INSERT_SQL)) {
            for (int i=0; i<3; i++) {
                String ap = "ap" + i;
                insertLog(database, ap, counter);
            }
        }
    }
    
    interface TestBlock {
        void execute() throws Exception;
    }
    
    private static void stopWatch(TestBlock runnable) throws Exception {
        System.out.println("start");
        
        long start = System.currentTimeMillis();
        runnable.execute();
        long end = System.currentTimeMillis();

        System.out.println();
        System.out.println((end - start) + "ms");
    }
    
    private static LocalDateTime now = LocalDateTime.now();
    
    private static void insertLog(Database database, String ap, AtomicInteger counter) throws SQLException {
        for (int i=0; i<900_000; i++) {
            String user = "user" + (i%4);

            database.update(user, now.plus(i, ChronoUnit.MILLIS), i, ap, UUID.randomUUID().toString());

            int count = counter.incrementAndGet();
            if (count % 10000 == 0) {
                System.out.print(".");
            }
        }
    }
    
    private static class Database implements AutoCloseable {
        private final Connection con;
        private final String sql;
        private PreparedStatement ps;
        private int batchCounter;
        
        private static void update(Connection con, String sql, Object... parameters) {
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                setParameter(ps, parameters);
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        private Database(Connection con, String sql) {
            this.con = con;
            this.sql = sql;
        }

        private void update(Object... parameters) throws SQLException {
            if (batchUpdate) {
                if (ps == null) {
                    ps = con.prepareStatement(sql);
                }

                setParameter(ps, parameters);
                ps.addBatch();

                batchCounter++;
                if (batchCounter % 10000 == 0) {
                    ps.executeBatch();
                    ps.close();
                    ps = con.prepareStatement(sql);
                    batchCounter = 0;
                }
            } else {
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    setParameter(ps, parameters);
                    ps.executeUpdate();
                }
            }
        }

        private static void setParameter(PreparedStatement ps, Object... parameters) throws SQLException {
            for (int i=1; i<=parameters.length; i++) {
                Object param = parameters[i-1];
                if (param instanceof LocalDateTime) {
                    Timestamp timestamp = Timestamp.valueOf(((LocalDateTime) param));
                    ps.setTimestamp(i, timestamp );
                } else {
                    ps.setObject(i, param);
                }
            }
        }

        @Override
        public void close() {
            try {
                if (0 < batchCounter) {
                    ps.executeBatch();
                    ps.close();
                    batchCounter = 0;
                }
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
