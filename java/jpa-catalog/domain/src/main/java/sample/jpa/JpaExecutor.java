package sample.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;

public class JpaExecutor {
    public static void execute(String name) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("SampleUnit");
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            TypedQuery<EntityAlpha> query = em.createQuery("select a from EntityAlpha a where a.embeddableAlpha = :value", EntityAlpha.class);
            query.setParameter("value", new EmbeddableAlpha("aaa"));
            List<EntityAlpha> resultList = query.getResultList();
            resultList.forEach(System.out::println);

//            TypedQuery<EntityAlpha> query = em.createQuery("select a from EntityAlpha a order by a.id asc", EntityAlpha.class);
//            List<EntityAlpha> list = query.getResultList();
//            list.forEach(a -> System.out.println("*** " + a));
//
//            if (list.size() == 1) {
//                EntityAlpha first = list.get(0);
//                first.update("update(" + name + ")");
//            } else if (list.size() == 2) {
//                EntityAlpha first = list.get(0);
//                em.remove(first);
//
//                EntityAlpha second = list.get(1);
//                second.update("UPDATE(" + name + ")");
//            }
//
//            List<EntityBeta> betaList = Arrays.asList(
//                    new EntityBeta("foo"),
//                    new EntityBeta("bar")
//            );
//
//            String n = "insert(" + name + ")";
//            EntityAlpha insert = new EntityAlpha(n, betaList);
//            em.persist(insert);

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
