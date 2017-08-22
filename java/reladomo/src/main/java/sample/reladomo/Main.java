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

        try (
            TablePrinter tablePrinter = new TablePrinter();
            TableWriter tableWriter = new TableWriter();
        ) {
            System.out.println("* insert foo");
            SampleTable insertedFoo = insertSampleTable("foo");

            System.out.println("* print table");
            tablePrinter.print("sample_table");

            System.out.println("* find foo 1");
            SampleTable beforeUpdateFoo = SampleTableFinder.findOne(SampleTableFinder.name().eq("foo"));
            System.out.println("beforeUpdateFoo = " + beforeUpdateFoo);
            System.out.println("insertedFoo == beforeUpdateFoo > " + (insertedFoo == beforeUpdateFoo));

            System.out.println("* update foo to bar");
            tableWriter.write("update sample_table set name=? where id=?", "bar", beforeUpdateFoo.getId());

            System.out.println("* print table");
            tablePrinter.print("sample_table");
            
            System.out.println("* find foo 2");
            SampleTable afterUpdateFoo = SampleTableFinder.findOne(SampleTableFinder.name().eq("foo"));
            System.out.println("afterUpdateFoo = " + afterUpdateFoo);
            System.out.println("beforeUpdateFoo == afterUpdateFoo > " + (beforeUpdateFoo == afterUpdateFoo));

            System.out.println("* find bar");
            SampleTable bar = SampleTableFinder.findOne(SampleTableFinder.name().eq("bar"));
            System.out.println("beforeUpdateFoo == bar > " + (beforeUpdateFoo == bar));
        }
    }
    
    private static SampleTable insertSampleTable(String name) {
        SampleTable sampleTable = new SampleTable();
        sampleTable.setName(name);
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
        } catch (IOException e) {
            throw new RuntimeException("failed to prepare ConnectionManager", e);
        }
    }
}