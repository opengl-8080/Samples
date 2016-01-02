package sample.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface TestTableMapper {
    
    List<TestTable> selectByStringOrNumber(
        @Param("string") String string,
        @Param("number") int number
    );
}
