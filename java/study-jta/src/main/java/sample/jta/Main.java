package sample.jta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");
        try (Connection con = getConnection();
             Statement stmt = con.createStatement()) {
            stmt.executeUpdate("create table if not exists foo(id number(10), code varchar(20))");

            stmt.executeUpdate("insert into foo values (1, 'hoge')");
            stmt.executeUpdate("insert into foo values (2, 'fuga')");
        }

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("select * from foo");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                long id = rs.getLong("id");
                String code = rs.getString("code");
                System.out.println("id=" + id + ", code=" + code);
            }
        }
    }
    
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
    }
}
