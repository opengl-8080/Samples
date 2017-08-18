package sample.reladomo;

import com.gs.fw.common.mithra.MithraManagerProvider;
import com.gs.fw.common.mithra.finder.Operation;
import org.h2.tools.RunScript;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Main {
    
    public static void main(String[] args) throws Exception {
        prepareDatabase();
        prepareConnectionManager();

        // DB に SampleTable のインスタンスを保存
        SampleTable newInstance = new SampleTable();
        newInstance.setName("foo");
        newInstance.setUpdateDate(Timestamp.valueOf(LocalDateTime.now()));
        newInstance.insert();

        // DB から SampleTable のインスタンスを取得
        printSampleTable();
        
        try {
            MithraManagerProvider.getMithraManager().executeTransactionalCommand(tx -> {
                Operation op2 = SampleTableFinder.name().eq("foo");
                SampleTable data = SampleTableFinder.findOne(op2);
                data.setName("FOO");
                printSampleTable();
//                throw new RuntimeException("test");
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        printSampleTable();
    }
    
    private static void prepareDatabase() {
        try (
            Connection con = MyConnectionManager.getInstance().getConnection();
            Reader ddl = new InputStreamReader(Main.class.getResourceAsStream("/sql/ddl/create_tables.sql"));
        ) {
            RunScript.execute(con, ddl);
        } catch (SQLException | IOException e) {
            throw new RuntimeException("failed to prepare database.", e);
        }
    }
    
    private static void prepareConnectionManager() {
        try (InputStream config = Main.class.getResourceAsStream("/ReladomoConfig.xml")) {
            MithraManagerProvider.getMithraManager().readConfiguration(config);
        } catch (IOException e) {
            throw new RuntimeException("failed to prepare ConnectionManager", e);
        }
    }
    
    private static void printSampleTable() {
        try (
            Connection con = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from sample_table");
        ) {
            while (rs.next()) {
                long id = rs.getLong("ID");
                String name = rs.getString("NAME");
                LocalDateTime updateDate = rs.getTimestamp("UPDATE_DATE").toLocalDateTime();

                System.out.println("id=" + id + ", name=" + name + ", updateDate=" + updateDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
