package sample.doma2;

import java.util.List;

import org.seasar.doma.jdbc.tx.TransactionManager;

public class Main {
    
    public static void main(String[] args) {
        TransactionManager tm = MyConfig.singleton().getTransactionManager();
        TestTableDao dao = new TestTableDaoImpl();
        
        tm.required(() -> {
            List<TestTable> list = dao.selectAll();
            list.forEach(System.out::println);
            
            TestTable testTable = new TestTable("hahaha");
            dao.insert(testTable);
            System.out.println(testTable);
            
            testTable.setValue("fufuf");
            dao.update(testTable);
            
            TestTable next = dao.selectRecently();
            System.out.println(next);
            
            dao.delete(next);
            
            dao.selectAll().forEach(System.out::println);
        });
    }
}
