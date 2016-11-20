package sample.jpa;

import item.Item;
import item.ItemName;
import item.ItemUnitPrice;
import order.DeliveryDate;
import order.OrderRequest;
import order.OrderRequestDetail;
import order.Quantity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class JpaExecutor {
    public static void execute(String name) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("SampleUnit");
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Item item1 = new Item(new ItemName("item1"), new ItemUnitPrice(300));
            Item item2 = new Item(new ItemName("item2"), new ItemUnitPrice(500));
            Item item3 = new Item(new ItemName("item3"), new ItemUnitPrice(1200));

            em.persist(item1);
            em.persist(item2);
            em.persist(item3);

//            TypedQuery<OrderRequest> query = em.createQuery("select orderRequest from OrderRequest orderRequest where orderRequest.id = 1", OrderRequest.class);
//            OrderRequest singleResult = query.getSingleResult();
//            System.out.println(singleResult);

//            TypedQuery<Item> query = em.createQuery("select i from Item i order by i.id", Item.class);
//            List<Item> items = query.getResultList();
//            Item item1 = items.get(0);
//            Item item2 = items.get(1);
//            Item item3 = items.get(2);
//
//            OrderRequest orderRequest1 = new OrderRequest(new DeliveryDate(new Date()), Arrays.asList(
//                    new OrderRequestDetail(item1, new Quantity(3)),
//                    new OrderRequestDetail(item2, new Quantity(1))
//                ));
//
//            em.persist(orderRequest1);
//            OrderRequest orderRequest2 = new OrderRequest(new DeliveryDate(new Date()), Arrays.asList(
//                    new OrderRequestDetail(item3, new Quantity(2)),
//                    new OrderRequestDetail(item2, new Quantity(4)),
//                    new OrderRequestDetail(item1, new Quantity(5))
//            ));
//            em.persist(orderRequest2);

//            TypedQuery<OrderRequest> query1 = em.createQuery("select orderRequest from OrderRequest orderRequest where orderRequest.id = 2", OrderRequest.class);
//            OrderRequest order = query1.getSingleResult();
//            order.update();
//            em.remove(order);

//            TypedQuery<EntityAlpha> query = em.createQuery("select a from EntityAlpha a where a.embeddableAlpha.value < (:value).value", EntityAlpha.class);
//            query.setParameter("value", 6);
//            query.setParameter("value2", new EmbeddableAlpha("ccc"));
//            List<EntityAlpha> resultList = query.getResultList();
//            resultList.forEach(System.out::println);

//            first(em, name + "(1)");
//            second(em, name + "(2)");
//            third(em, name + "(3)");
//            fourth(em);

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
