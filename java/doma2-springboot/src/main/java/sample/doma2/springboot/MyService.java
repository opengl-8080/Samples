package sample.doma2.springboot;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyService {
    
    @Autowired
    private TestTableDao dao;
    
    @Transactional
    public void updateAndThorwException(int id, String value, Supplier<Exception> exceptionSupplier) throws Exception {
        TestTable testTable = this.dao.findById(id);
        testTable.setValue(value);
        
        this.dao.update(testTable);
        
        throw exceptionSupplier.get();
    }
    
    public void printAll() {
        this.dao.findAll().forEach(System.out::println);
    }
}
