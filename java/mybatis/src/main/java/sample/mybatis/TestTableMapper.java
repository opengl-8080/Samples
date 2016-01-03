package sample.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;

public interface TestTableMapper {
    
    @SelectProvider(type=TestTableSqlProvider.class, method="getSelectTestSql")
    List<TestTable> selectTest();
    
}
