package sample.jta;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Server server = Server.createWebServer().start();

        System.out.println("hoge");

        Class.forName("org.h2.Driver");
        try (Connection con = DriverManager.getConnection("jdbc:h2:localhost/test", "sa", "")) {
            try (Statement stmt = con.createStatement()) {
                stmt.executeUpdate("create table foo(id number(10), code varchar(20))");

                stmt.executeUpdate("insert into foo values (1, 'hoge')");
                stmt.executeUpdate("insert into foo values (2, 'fuga')");
                
                try (ResultSet rs = stmt.executeQuery("select * from foo")) {
                    while (rs.next()) {
                        long id = rs.getLong("id");
                        String code = rs.getString("code");
                        System.out.println("id=" + id + ", code=" + code);
                    }
                }
            }
        }

        server.stop();
    }
}
