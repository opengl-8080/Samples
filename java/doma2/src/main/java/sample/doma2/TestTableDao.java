package sample.doma2;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.jdbc.SelectOptions;

@Dao(config=MyConfig.class)
public interface TestTableDao {
    
    @Select
    List<TestTable> findAll(SelectOptions options);
}
