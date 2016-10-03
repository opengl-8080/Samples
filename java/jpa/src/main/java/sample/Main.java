package sample;

import sample.jpa.EntityAlpha;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws Exception {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("SampleUnit");
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            TypedQuery<EntityAlpha> query = em.createQuery("select e from EntityAlpha e", EntityAlpha.class);
            query.getResultList().forEach(System.out::println);

            EntityAlpha a = new EntityAlpha("a", false, 2, 3.1, new BigInteger("123"), new BigDecimal("3.2"), new Date(), new Date());
            em.persist(a);

            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }
}
