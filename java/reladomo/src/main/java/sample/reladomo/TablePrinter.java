package sample.reladomo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TablePrinter implements AutoCloseable {
    
    private final Connection con;

    public TablePrinter() {
        try {
            this.con = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void print(String tableName) {
        try (
            PreparedStatement ps = this.con.prepareStatement("select * from " + tableName);
            ResultSet rs = ps.executeQuery();
        ) {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            
            while (rs.next()) {
                List<String> columnValues = new ArrayList<>();
                
                for (int columnNumber=1; columnNumber<=columnCount; columnNumber++) {
                    String columnName = metaData.getColumnName(columnNumber);
                    Object value = rs.getObject(columnName);
                    columnValues.add(columnName + "=" + value);
                }

                System.out.println(String.join(", ", columnValues));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        this.con.close();
    }
}
