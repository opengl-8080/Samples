package sample.infrastructure.order;

import sample.domain.Key;
import sample.domain.order.Customer;
import sample.domain.order.CustomerCode;
import sample.domain.order.DueDate;
import sample.domain.order.ItemCode;
import sample.domain.order.ItemName;
import sample.domain.order.Order;
import sample.domain.order.OrderDetail;
import sample.domain.order.OrderNumber;
import sample.domain.order.OrderRepository;
import sample.domain.order.Price;
import sample.domain.order.Quantity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcOrderRepository implements OrderRepository {

    private Connection con;

    @Override
    public Order find(OrderNumber orderNumber) {
        String sql = "SELECT * FROM ORDERS WHERE ORDER_NUMBER=?";
        try (PreparedStatement ps = this.con.prepareStatement(sql)) {
            ps.setString(1, orderNumber.getValue());

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new RuntimeException("Entity is not found.");
                }

                JdbcCustomerRepository customerRepository = new JdbcCustomerRepository();
                customerRepository.setCon(this.con);
                Customer customer = customerRepository.find(new CustomerCode(rs.getString("customer_code")));

                List<OrderDetail> orderDetails = this.findOrderDetail(orderNumber);

                DueDate dueDate = new DueDate(rs.getDate("due_date"));

                return new Order(orderNumber, customer, dueDate, orderDetails);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<OrderDetail> findOrderDetail(OrderNumber orderNumber) throws SQLException {
        try (PreparedStatement ps = this.con.prepareStatement("SELECT * FROM ORDER_DETAILS WHERE ORDER_NUMBER=?")) {
            ps.setString(1, orderNumber.getValue());

            try (ResultSet rs = ps.executeQuery()) {
                List<OrderDetail> list = new ArrayList<>();

                while (rs.next()) {
                    Key<OrderDetail> id = new Key<>(rs.getLong("id"));
                    ItemCode itemCode = new ItemCode(rs.getString("item_code"));
                    Price price = new Price(rs.getBigDecimal("price"));
                    ItemName itemName = new ItemName(rs.getString("item_name"));
                    Quantity quantity = new Quantity(rs.getInt("quantity"));

                    OrderDetail orderDetail = new OrderDetail(id, itemCode, price, itemName, quantity);
                    list.add(orderDetail);
                }

                return list;
            }
        }
    }

    @Override
    public void register(Order order) {

    }

    public void setCon(Connection con) {
        this.con = con;
    }
}
