package sample.doma2.springboot;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

@Dao
@ConfigAutowireable
public interface TestTableDao {
    
    @Select
    List<TestTable> findAll();
}
