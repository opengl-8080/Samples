package sample;

import com.mysql.jdbc.MySQLConnection;
import sample.domain.order.Order;
import sample.domain.order.OrderNumber;
import sample.infrastructure.order.JdbcOrderRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.sql.Connection;
import java.sql.DriverManager;

public class Main {

    public static void main(String[] args) throws Exception {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/jpa?user=test&password=test");
        JdbcOrderRepository repository = new JdbcOrderRepository();
        repository.setCon(con);

        Order order = repository.find(new OrderNumber("O000000001"));
        System.out.println(order);

//
//        EntityManagerFactory factory = Persistence.createEntityManagerFactory("SampleUnit");
//        EntityManager em = factory.createEntityManager();
//        EntityTransaction tx = em.getTransaction();
//
//        tx.begin();
//
//        try {
//            TypedQuery<Order> query = em.createNamedQuery(Order.FIND_ALL, Order.class);
//            for (Order order : query.getResultList()) {
//                System.out.println(order);
//
//            }
//            tx.commit();
//        } finally {
//            if (tx.isActive()) {
//                tx.rollback();
//            }
//        }
    }
}
