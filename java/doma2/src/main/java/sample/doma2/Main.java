package sample.doma2;

import java.util.List;

import org.seasar.doma.jdbc.tx.TransactionManager;

public class Main {
    
    public static void main(String[] args) {
        TransactionManager tm = MyConfig.singleton().getTransactionManager();
        TestTableDao dao = new TestTableDaoImpl();
        
        tm.required(() -> {
            List<TestTable> list = dao.selectAll();
            System.out.println(list);
        });
    }
}
