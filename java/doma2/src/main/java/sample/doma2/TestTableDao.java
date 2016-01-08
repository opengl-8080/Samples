package sample.doma2;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;

@Dao(config=MyConfig.class)
public interface TestTableDao {
    
    @Select
    TestTable findById(int id);
}
