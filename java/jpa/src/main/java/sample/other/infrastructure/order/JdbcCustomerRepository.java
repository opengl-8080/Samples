package sample.other.infrastructure.order;

import sample.other.domain.customer.Address;
import sample.other.domain.customer.Customer;
import sample.other.domain.customer.CustomerCode;
import sample.other.domain.customer.CustomerName;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcCustomerRepository {

    private Connection con;

    public Customer find(CustomerCode code) {
        try (PreparedStatement ps = this.con.prepareStatement("SELECT * FROM CUSTOMERS WHERE CODE=?")) {
            ps.setString(1, code.getValue());

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new RuntimeException("Entity is not found.");
                }

                CustomerName name = new CustomerName(rs.getString("name"));
                Address address = new Address(rs.getString("address"));

                return new Customer(code, name, address);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCon(Connection con) {
        this.con = con;
    }
}
