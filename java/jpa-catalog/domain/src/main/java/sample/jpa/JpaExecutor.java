package sample.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class JpaExecutor {
    public static void execute(String name) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("SampleUnit");
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            TypedQuery<EntityAlpha> query = em.createQuery("select a from EntityAlpha a", EntityAlpha.class);
            System.out.println("************************************************************");
            List<EntityAlpha> list = query.getResultList();
            list.forEach(System.out::println);
            list.get(0).update(name + "9", name + "Z");

            System.out.println("************************************************************");

            EntityAlpha entity = new EntityAlpha(name + "1", new EntityBeta(name + "A", name + "B", name));
            em.persist(entity);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }
}
