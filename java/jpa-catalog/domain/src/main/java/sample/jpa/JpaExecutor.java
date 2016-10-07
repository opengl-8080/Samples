package sample.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;

public class JpaExecutor {
    public static void execute(String name) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("SampleUnit");
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            TypedQuery<EntityAlpha> query = em.createQuery("select a from EntityAlpha a", EntityAlpha.class);
            System.out.println("************************************************************");
            query.getResultList().forEach(e -> {
                System.out.println(e);
                e.test();
            });
            System.out.println("************************************************************");


            Map<EmbeddableAlpha, String> map = new HashMap<>();
            map.put(new EmbeddableAlpha("a"), "AAA");
            map.put(new EmbeddableAlpha("b"), "BBB");
            map.put(new EmbeddableAlpha("c"), "CCC");

            EntityAlpha entity = new EntityAlpha(name, map);
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
