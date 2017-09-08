package sample.reladomo;

import com.gs.fw.common.mithra.MithraManagerProvider;
import com.sun.jmx.snmp.SnmpUnknownAccContrModelException;
import org.h2.tools.RunScript;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) throws Exception {
        prepareDatabase();
        prepareConnectionManager();

        try (TablePrinter tablePrinter = new TablePrinter()) {
            MithraManagerProvider.getMithraManager().executeTransactionalCommand(tx -> {
                insertSampleTable();
                return null;
            });
            tablePrinter.print("sample_table");
            MithraManagerProvider.getMithraManager().executeTransactionalCommand(tx -> {
                Timestamp now = Timestamp.valueOf(LocalDate.now().plusDays(1).atStartOfDay());
                SampleTable foo = SampleTableFinder.findOne(
                        SampleTableFinder.code().eq("FOO")
                                .and(SampleTableFinder.processingDate().equalsInfinity())
                );
                System.out.println(foo);
                foo.setValue("update foo");
                return null;
            });
            
            tablePrinter.print("sample_table");
            
        }
    }
    
    private static SampleTable insertSampleTable() {
        SampleTable sampleTable = new SampleTable();
        sampleTable.setCode("FOO");
        sampleTable.setValue("original foo");
        sampleTable.insert();
        return sampleTable;
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