package sample;

import org.apache.commons.dbcp2.BasicDataSource;

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

public class Main {
    static DataSource dataSource;
    public static void main(String[] args) throws Exception {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:h2:./build/database");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setInitialSize(4);
        dataSource.setMaxTotal(4);
        Main.dataSource = dataSource;
        
        try (Connection con = dataSource.getConnection()) {
            update(con, "drop table logs");
            update(con,"create table logs (" +
                            "  user_name nvarchar2(64) not null," +
                            "  date_time timestamp not null," +
                            "  sequence_number integer not null," +
                            "  ap_name nvarchar2(64) not null," +
                            "  message nvarchar(256) not null" +
                            ")");
        }
        
        stopWatch(() -> {
//            test_single_thread_standalone_db();
            test_multi_thread_standalone_db();
        });
    }
    
    // 25482ms
    private static void test_multi_thread_standalone_db() throws SQLException {
        List<Callable<Void>> tasks = new ArrayList<>();

        for (int i=0; i<3; i++) {
            String ap = "ap" + i;
            
            tasks.add(() -> {
                try (Connection con = dataSource.getConnection()) {
                    insertLog(con, ap);
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
    
    // 23692ms
    private static void test_single_thread_standalone_db() throws SQLException {
        try (Connection con = dataSource.getConnection()) {
            for (int i=0; i<3; i++) {
                String ap = "ap" + i;
                insertLog(con, ap);
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

        System.out.println((end - start) + "ms");
    }
    
    private static LocalDateTime now = LocalDateTime.now();
    
    private static void insertLog(Connection con, String ap) throws SQLException {
        for (int i=0; i<900_000; i++) {
            String user = "user" + (i%4);

            update(con, "insert into logs values (?, ?, ?, ?, ?)",
                    user, now.plus(i, ChronoUnit.MILLIS), i, ap, UUID.randomUUID().toString());
        }
    }
    
    private static void update(Connection con, String sql, Object... parameters) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i=1; i<=parameters.length; i++) {
                Object param = parameters[i-1];
                if (param instanceof LocalDateTime) {
                    Timestamp timestamp = Timestamp.valueOf(((LocalDateTime) param));
                    ps.setTimestamp(i, timestamp);
                } else {
                    ps.setObject(i, param);
                }
            }
            
            ps.executeUpdate();
        }
    }
}
