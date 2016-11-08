package sample.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class JpaExecutor {
    public static void execute(String name) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("SampleUnit");
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
//            TypedQuery<EntityAlpha> query = em.createQuery("select a from EntityAlpha a where a.embeddableAlpha.value < (:value).value", EntityAlpha.class);
//            query.setParameter("value", 6);
//            query.setParameter("value2", new EmbeddableAlpha("ccc"));
//            List<EntityAlpha> resultList = query.getResultList();
//            resultList.forEach(System.out::println);

            exec(em, EntityAlpha.class, name);
            exec(em, EntityBeta.class, name);
            exec(em, EntityGamma.class, name);

            EntityAlpha alpha = new EntityAlpha("insert(" + name + ")");
            em.persist(alpha);

            EntityBeta beta = new EntityBeta("Insert[" + name + "]", "Insert{" + name + "}");
            em.persist(beta);

            EntityGamma gamma = new EntityGamma("INSERT'" + name + "'", "INSERT\"" + name + "\"", "INSERT|" + name + "|");
            em.persist(gamma);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    private static <T extends EntityAlpha> void exec(EntityManager em, Class<T> clazz, String name) {
        TypedQuery<T> query = em.createQuery("select a from " + clazz.getSimpleName() + " a order by a.id asc", clazz);
        List<T> list = query.getResultList();
        System.out.println("===" + clazz.getSimpleName() + "===");
        list.forEach(System.out::println);
        if (list.size() == 1) {
            list.get(0).update(name);
        } else if (2 < list.size()) {
            em.remove(list.get(0));
        }
    }
}
