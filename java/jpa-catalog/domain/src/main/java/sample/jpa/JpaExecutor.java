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
//            TypedQuery<EntityAlpha> query = em.createQuery("select a from EntityAlpha a where a.embeddableAlpha.value < (:value).value", EntityAlpha.class);
//            query.setParameter("value", 6);
//            query.setParameter("value2", new EmbeddableAlpha("ccc"));
//            List<EntityAlpha> resultList = query.getResultList();
//            resultList.forEach(System.out::println);

//            first(em, name);
//            second(em, name);
//            third(em, name);
            fourth(em);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    private static void first(EntityManager em, String name) {
        EntityAlpha alpha = new EntityAlpha(name);
        EntityGamma gamma = new EntityGamma(name, alpha);
        em.persist(gamma);

        EntityBeta beta = new EntityBeta("name(" + name + ")", "code(" + name + ")");
        EntityDelta delta = new EntityDelta(name, beta);
        em.persist(delta);
    }

    private static void second(EntityManager em, String name) {
        TypedQuery<EntityGamma> query = em.createQuery("select a from EntityGamma a", EntityGamma.class);
        query.getResultList().forEach(System.out::println);

        TypedQuery<EntityDelta> query2 = em.createQuery("select a from EntityDelta a", EntityDelta.class);
        query2.getResultList().forEach(System.out::println);
    }

    private static void third(EntityManager em, String name) {
        TypedQuery<EntityGamma> query = em.createQuery("select a from EntityGamma a", EntityGamma.class);
        query.getResultList().get(0).update("UPDATE(" + name + ")");

        TypedQuery<EntityDelta> query2 = em.createQuery("select a from EntityDelta a", EntityDelta.class);
        query2.getResultList().get(0).update("UPDATE(" + name + ")");
    }

    private static void fourth(EntityManager em) {
        TypedQuery<EntityGamma> query = em.createQuery("select a from EntityGamma a", EntityGamma.class);
        em.remove(query.getResultList().get(0));

        TypedQuery<EntityDelta> query2 = em.createQuery("select a from EntityDelta a", EntityDelta.class);
        em.remove(query2.getResultList().get(0));
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
