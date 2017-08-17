package sample.reladomo;

import com.gs.fw.common.mithra.MithraManagerProvider;
import com.gs.fw.common.mithra.finder.Operation;
import org.h2.tools.RunScript;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
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
        Operation operation = SampleTableFinder.name().eq("foo");
        SampleTable fromDatabase = SampleTableFinder.findOne(operation);
        System.out.println(
            "id=" + fromDatabase.getId() + ", " +
            "name=" + fromDatabase.getName() + ", " +
            "updateDate=" + fromDatabase.getUpdateDate()
        );
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
}
