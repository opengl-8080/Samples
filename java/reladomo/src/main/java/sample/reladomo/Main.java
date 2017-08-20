package sample.reladomo;

import com.gs.fw.common.mithra.MithraManagerProvider;
import org.h2.tools.RunScript;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws Exception {
        prepareDatabase();
        prepareConnectionManager();

        SampleTable foo = new SampleTable();
        foo.setName("foo");
        foo.insert();

        SampleTable savedFoo = SampleTableFinder.findOne(SampleTableFinder.name().eq("foo"));
        savedFoo.setName("FOO");
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