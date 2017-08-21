package sample.reladomo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TableWriter implements AutoCloseable {

    private final Connection con;

    public TableWriter() {
        try {
            this.con = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void write(String sql, Object... parameters) {
        try (PreparedStatement ps = this.con.prepareStatement(sql)) {
            for (int i=1; i<=parameters.length; i++) {
                ps.setObject(i, parameters[i-1]);
            }
            
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        this.con.close();
    }
}
