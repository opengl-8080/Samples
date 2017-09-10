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

        try (TablePrinter tablePrinter = new TablePrinter()) {
            tablePrinter.print("sample_table");

            System.out.println(findById(1L));
            System.out.println(findByIdAndProcessDate(1L, 2017, 1, 1));
            System.out.println(findByIdAndProcessDate(1L, 2017, 1, 2));
            System.out.println(findByIdAndProcessDate(1L, 2017, 1, 3));
            System.out.println(findByIdAndProcessDate(1L, 2017, 1, 4));
        }
    }

    private static SampleTable findById(long id) {
        Operation operation = SampleTableFinder.id().eq(id);
        return SampleTableFinder.findOne(operation);
    }
    
    private static SampleTable findByIdAndProcessDate(long id, int year, int month, int dayOfMonth) {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.of(year, month, dayOfMonth, 0, 0));
        System.out.println("timestamp=" + timestamp);
        Operation operation = SampleTableFinder.id().eq(id)
                                .and(SampleTableFinder.processingDate().eq(timestamp));
        return SampleTableFinder.findOne(operation);
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