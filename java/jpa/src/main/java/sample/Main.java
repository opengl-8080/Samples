package sample;

import sample.jpa.EntityAlpha;
import sample.jpa.EnumAlpha;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws Exception {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("SampleUnit");
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            TypedQuery<EntityAlpha> query = em.createQuery("select e from EntityAlpha e", EntityAlpha.class);
            System.out.println("*********************************************************************");
            query.getResultList().forEach(System.out::println);
            System.out.println("*********************************************************************");

            EntityAlpha entityAlpha = new EntityAlpha(Arrays.asList("A", "B", "C"));
            em.persist(entityAlpha);

            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }
}
