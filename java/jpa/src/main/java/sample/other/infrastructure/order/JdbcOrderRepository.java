package sample.other.infrastructure.order;

import sample.other.domain.Key;
import sample.other.domain.customer.Customer;
import sample.other.domain.customer.CustomerCode;
import sample.other.domain.order.DueDate;
import sample.other.domain.item.ItemCode;
import sample.other.domain.item.ItemName;
import sample.other.domain.order.Order;
import sample.other.domain.order.OrderDetail;
import sample.other.domain.order.OrderNumber;
import sample.other.domain.order.OrderRepository;
import sample.other.domain.item.Price;
import sample.other.domain.order.Quantity;

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
