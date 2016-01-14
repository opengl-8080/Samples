package sample.doma2.springboot;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyService {
    
    @Autowired
    private TestTableDao dao;
    
    @Transactional
    public void method() {
        TestTable testTable = dao.findAll().get(0);
        testTable.setValue(String.valueOf((new Random(new Date().getTime()).nextInt() % 1000)));
        
        this.dao.update(testTable);
        throw new RuntimeException("test exception");
    }
}
