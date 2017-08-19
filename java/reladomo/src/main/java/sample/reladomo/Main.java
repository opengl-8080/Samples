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
        
        SampleTableList list1 = new SampleTableList(SampleTableFinder.all());
        SampleTableList list2 = new SampleTableList(SampleTableFinder.all());

        printList("list1", list1);
        
        MithraManagerProvider.getMithraManager().executeTransactionalCommand(tx -> {
            insertSampleTable("foo");
            insertSampleTable("bar");

            return null;
        });

        printList("list1", list1);
        printList("list2", list2);

        MithraManagerProvider.getMithraManager().executeTransactionalCommand(tx -> {
            insertSampleTable("fizz");

            return null;
        });
        
        printList("list2", list2);
    }
    
    private static void insertSampleTable(String name) {
        SampleTable sampleTable = new SampleTable();
        sampleTable.setName(name);
        sampleTable.insert();
        System.out.println("* insert " + name + ". hashCode=" + sampleTable.hashCode());
        System.out.println();
    }
    
    private static void printList(String listName, SampleTableList list) {
        System.out.println("[" + listName + "]");
        list.forEach(sampleTable -> {
            System.out.printf("id=%s, name=%s, hash=%s%n", sampleTable.getId(), sampleTable.getName(), sampleTable.hashCode());
        });
        System.out.println();
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