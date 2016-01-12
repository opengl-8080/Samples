package sample.doma2.javaee;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;

@Dao
@InjectMyConfig
public interface TestTableDao {
    
    @Select
    List<TestTable> findAll();
}
