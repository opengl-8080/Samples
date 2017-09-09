package sample.reladomo;

import com.gs.fw.common.mithra.MithraManager;
import com.gs.fw.common.mithra.MithraManagerProvider;
import com.gs.fw.common.mithra.finder.Operation;
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

        try (TablePrinter tablePrinter = new TablePrinter()) {
            MithraManager manager = MithraManagerProvider.getMithraManager();
            manager.executeTransactionalCommand(tx -> {
                SampleTable foo = new SampleTable();
                foo.setValue("FOO");
                foo.insert();
                
                return null;
            });
            
            tablePrinter.print("sample_table");
            
            manager.executeTransactionalCommand(tx -> {
                Operation operation = SampleTableFinder.value().eq("FOO");
                SampleTable foo = SampleTableFinder.findOne(operation);
                foo.setValue("update foo");
                
                return null;
            });
            
            tablePrinter.print("sample_table");
        }
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
            MithraManagerProvider.getMithraManager().setTransactionTimeout(600);
        } catch (IOException e) {
            throw new RuntimeException("failed to prepare ConnectionManager", e);
        }
    }
}