package sample.doma2;

import java.util.List;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Update;

@Dao(config=MyConfig.class)
public interface TestTableDao {
    
    @Select
    List<TestTable> selectAll();
    
    @Select
    TestTable selectRecently();
    
    @Insert
    int insert(TestTable testTable);
    
    @Update
    int update(TestTable testTable);
    
    @Delete
    int delete(TestTable testTable);
}
