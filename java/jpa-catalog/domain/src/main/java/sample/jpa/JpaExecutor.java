package sample.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JpaExecutor {
    public static void execute(String name) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("SampleUnit");
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            TypedQuery<EntityAlpha> query = em.createQuery("select a from EntityAlpha a order by a.id asc", EntityAlpha.class);
            List<EntityAlpha> list = query.getResultList();
            list.forEach(a -> System.out.println("*** " + a));

            if (list.size() == 1) {
                EntityAlpha first = list.get(0);
                first.update("update(" + name + ")");
            } else if (list.size() == 2) {
                EntityAlpha first = list.get(0);
                em.remove(first);

                EntityAlpha second = list.get(1);
                second.update("UPDATE(" + name + ")");
            }

            Map<EmbeddableAlpha, EntityBeta> map = new HashMap<>();
            map.put(new EmbeddableAlpha("foo"), new EntityBeta(name + "[1]"));
            map.put(new EmbeddableAlpha("bar"), new EntityBeta(name + "[2]"));

            String n = "insert(" + name + ")";
            EntityAlpha insert = new EntityAlpha(n, map);
            em.persist(insert);

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
