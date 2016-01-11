package sample.doma2;

import org.seasar.doma.jdbc.SelectOptions;
import org.seasar.doma.jdbc.tx.TransactionManager;

public class Main {
    
    public static void main(String[] args) {
        TransactionManager tm = MyConfig.singleton().getTransactionManager();
        TestTableDao dao = new TestTableDaoImpl();
        
        tm.required(() -> {
            SelectOptions options = SelectOptions.get().offset(1).limit(2);
            dao.findAll(options).forEach(System.out::println);
        });
    }
}
