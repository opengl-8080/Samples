package sample;

import org.apache.commons.dbcp2.BasicDataSource;
import org.h2.server.Service;
import org.h2.server.TcpServer;
import org.h2.tools.Server;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *    index, single thread, standalone: 57054ms
 *    index, single thread,     server: 71410ms
 *    index,  multi thread, standalone: 76987ms
 *    index,  multi thread,     server: 81032ms
 *    index,  multi thread,     server: 
 * no index, single thread, standalone: 71428ms
 * no index, single thread,     server: 85080ms
 * no index,  multi thread, standalone: 72043ms
 * no index,  multi thread,     server: 77562ms
 */
public class TestH2SelectPerformance {
    private static final boolean index = true;
    private static final boolean singleThread = false;
    private static final boolean standalone = false;
    
    private static Server server;
    private static DataSource dataSource;

    public static void main(String[] args) throws Exception {
//        TestH2InsertPerformance.main(args);
        
        if (index) {
            System.out.print("   index, ");
        } else {
            System.out.print("no index, ");
        }
        
        if (singleThread) {
            System.out.print("single thread, ");
        } else {
            System.out.print(" multi thread, ");
        }
        
        if (standalone) {
            System.out.print("standalone: ");
            initStandaloneDatabase();
        } else {
            System.out.print("    server: ");
            initServerDatabase();
        }

//        createIndex();

        System.out.println();
        stopWatch(() -> {
            if (singleThread) {
                test_single_thread();
            } else {
                test_multi_thread();
            }
        });
        
//        if (server != null) {
//            server.stop();
//        }
    }
    
    private static void test_single_thread() throws SQLException {
        try (Database database = new Database(dataSource.getConnection(), "select * from logs order by user_name, date_time, sequence_number")) {
            AtomicInteger counter = new AtomicInteger(0);
            database.executeQuery(rs -> {
                counter.incrementAndGet();
            });
            System.out.println("count=" + counter.get());
        }
    }
    
    private static void test_multi_thread() {
        List<Callable<Void>> tasks = new ArrayList<>();
        for (int i=0; i<4; i++) {
            int I = i;
            String user = "user" + i;
            
            tasks.add(() -> {
                try (Database database = new Database(dataSource.getConnection(), "select * from logs where user_name=? order by date_time, sequence_number")) {
                    BasicDataSource bds = (BasicDataSource) TestH2SelectPerformance.dataSource;
                    System.out.println("active=" + bds.getNumActive());
                    AtomicInteger counter = new AtomicInteger(0);
                    database.executeQuery(rs -> {
                        int ii = counter.incrementAndGet();
                        if (ii % 100000 ==0) {
                            System.out.print(I);
                        }
                    }, user);
                }
                System.out.println();
                return null;
            });
        }

        ExecutorService service = Executors.newFixedThreadPool(4);
        try {
            service.invokeAll(tasks);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            service.shutdown();
        }
    }
    
    private static void initStandaloneDatabase() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:h2:./build/database");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setInitialSize(4);
        dataSource.setMaxTotal(4);
        TestH2SelectPerformance.dataSource = dataSource;
    }

    private static void initServerDatabase() throws SQLException {
//        server = Server.createTcpServer();
//        server.start();

        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setUrl("jdbc:h2:tcp://localhost/./build/database");
        dataSource.setUrl("jdbc:h2:./build/database;AUTO_SERVER=TRUE");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setInitialSize(4);
        dataSource.setMaxTotal(4);
        TestH2SelectPerformance.dataSource = dataSource;
    }

    private static void createIndex() throws SQLException {
        if (!index) {
            return;
        }
        System.out.println("\nstart create index");
        try (Connection con = dataSource.getConnection()) {
            Database.update(con, "create index logs_idx on logs (user_name, date_time, sequence_number)");
        }
    }
    
    private static void stopWatch(TestBlock runnable) throws Exception {
        System.out.println("start");

        long start = System.currentTimeMillis();
        runnable.execute();
        long end = System.currentTimeMillis();

        System.out.println();
        System.out.println((end - start) + "ms");
    }
    
    private interface ResultSetIterator {
        void iterate(ResultSet rs) throws SQLException;
    }
    
    private static class Database implements AutoCloseable {
        private final Connection con;
        private final String sql;

        private Database(Connection con, String sql) {
            this.con = con;
            this.sql = sql;
        }
        
        private void executeQuery(ResultSetIterator iterator, Object... parameters) {
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                setParameter(ps, parameters);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        iterator.iterate(rs);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        
        
        private static void update(Connection con, String sql, Object... parameters) {
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                setParameter(ps, parameters);
                ps.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
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
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
