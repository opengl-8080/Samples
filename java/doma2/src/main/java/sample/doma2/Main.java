package sample.doma2;

import org.seasar.doma.jdbc.tx.TransactionManager;

public class Main {
    
    public static void main(String[] args) {
        TransactionManager tm = MyConfig.singleton().getTransactionManager();
        TestTableDao dao = new TestTableDaoImpl();
        
        tm.required(() -> {
            TestTable testTable = dao.findById(3);
            
            System.out.println(testTable);
        });
    }
}
