package sample.doma2;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;

@Dao(config=MyConfig.class)
public interface TestTableDao {
    
    @Select
    List<TestTable> selectAll();
}
