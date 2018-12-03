package sample.springboot.doma2;

import org.seasar.doma.Dao;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;

import java.util.List;

@Dao
@ConfigAutowireable
public interface TestTableDao {
    
    @Select
    List<TestTable> findAll();
    
    @Insert
    int insert(TestTable testTable);
}
