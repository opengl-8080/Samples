package sample.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class JpaExecutor {
    
    private static void doAndPrintQuery(EntityManager em, String jpql, Consumer<TypedQuery<EntityAlpha>> parameterSetter) {
        System.out.println("*********************************************************************");
        try {
            TypedQuery<EntityAlpha> query = em.createQuery(jpql, EntityAlpha.class);
            parameterSetter.accept(query);

            query.getResultList().forEach(System.out::println);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private static DateValue value(int year, int month, int day) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = formatter.parse(year + "-" + month + "-" + day);
            return new DateValue(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    
    private static TextValue value(String textValue) {
        return new TextValue(textValue);
    }
    
    private static NumberValue value(int numberValue) {
        return new NumberValue(numberValue);
    }

    private static MultipleValues value(int numberValue, String textValue, String dateValue) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = formatter.parse(dateValue);
            
            return new MultipleValues(numberValue, textValue, date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    public static void execute(String name) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("SampleUnit");
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            EntityBeta beta = em.find(EntityBeta.class, 3L);
            TypedQuery<EntityAlpha> query = em.createQuery(
                    "select a from EntityAlpha a join a.map m where key(m) = :key", EntityAlpha.class);
            query.setParameter("key", beta);
            
            query.getResultList().forEach(System.out::println);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

//    private static void first(EntityManager em, String name) {
//        EntityEpsilon epsilon = new EntityEpsilon(name);
//        EntityAlpha alpha = new EntityAlpha(name, epsilon);
//        EntityGamma gamma = new EntityGamma(name, alpha);
//        em.persist(gamma);
//
//        EntityEpsilon epsilon2 = new EntityEpsilon(name);
//        EntityZeta zeta = new EntityZeta(name);
//        EntityBeta beta = new EntityBeta("name(" + name + ")", epsilon2, "code(" + name + ")", zeta);
//        EntityDelta delta = new EntityDelta(name, beta);
//        em.persist(delta);
//    }
//
//    private static void second(EntityManager em, String name) {
//        TypedQuery<EntityGamma> query = em.createQuery("select a from EntityGamma a", EntityGamma.class);
//        query.getResultList().forEach(System.out::println);
//
//        TypedQuery<EntityDelta> query2 = em.createQuery("select a from EntityDelta a", EntityDelta.class);
//        query2.getResultList().forEach(System.out::println);
//    }
//
//    private static void third(EntityManager em, String name) {
//        TypedQuery<EntityGamma> query = em.createQuery("select a from EntityGamma a", EntityGamma.class);
//        query.getResultList().get(0).update("UPDATE(" + name + ")");
//
//        TypedQuery<EntityDelta> query2 = em.createQuery("select a from EntityDelta a", EntityDelta.class);
//        query2.getResultList().get(0).update("UPDATE(" + name + ")");
//    }
//
//    private static void fourth(EntityManager em) {
//        TypedQuery<EntityGamma> query = em.createQuery("select a from EntityGamma a", EntityGamma.class);
//        em.remove(query.getResultList().get(0));
//
//        TypedQuery<EntityDelta> query2 = em.createQuery("select a from EntityDelta a", EntityDelta.class);
//        em.remove(query2.getResultList().get(0));
//    }
//
//    private static <T extends EntityAlpha> void exec(EntityManager em, Class<T> clazz, String name) {
//        TypedQuery<T> query = em.createQuery("select a from " + clazz.getSimpleName() + " a order by a.id asc", clazz);
//        List<T> list = query.getResultList();
//        System.out.println("===" + clazz.getSimpleName() + "===");
//        list.forEach(System.out::println);
//        if (list.size() == 1) {
//            list.get(0).update(name);
//        } else if (2 < list.size()) {
//            em.remove(list.get(0));
//        }
//    }
}
