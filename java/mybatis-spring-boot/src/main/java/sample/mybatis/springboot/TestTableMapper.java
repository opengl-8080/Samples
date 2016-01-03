package sample.mybatis.springboot;

import java.util.List;
import java.util.Map;

public interface TestTableMapper {
    
    List<Map<String, Object>> selectTest();
}
