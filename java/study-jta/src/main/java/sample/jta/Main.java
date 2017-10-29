package sample.jta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("org.h2.Driver");

        try (Connection con = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "")) {
            try (Statement stmt = con.createStatement()) {
                stmt.executeUpdate("create table if not exists foo(id number(10), code varchar(20))");
            }

            try (PreparedStatement ps = con.prepareStatement("insert into foo values (?, ?)")) {
                ps.setLong(1, 1L);
                ps.setString(2, "hoge");
                ps.executeUpdate();

                ps.setLong(1, 2L);
                ps.setString(2, "fuga");
                ps.executeUpdate();
            }

            ResultSet rs;
            try (PreparedStatement ps = con.prepareStatement("select * from foo")) {
                rs = ps.executeQuery();
                while (rs.next()) {
                    long id = rs.getLong("id");
                    String code = rs.getString("code");
                    System.out.println("id=" + id + ", code=" + code);
                }
                System.out.println("(1) rs.isClosed()=" + rs.isClosed());
            }
            System.out.println("(2) rs.isClosed()=" + rs.isClosed());
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }
    }
}
