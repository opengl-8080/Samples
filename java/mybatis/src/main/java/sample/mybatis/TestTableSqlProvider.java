package sample.mybatis;

import org.apache.ibatis.jdbc.SQL;

public class TestTableSqlProvider {
    
    public String getSelectTestSql() {
        SQL sql = 
new SQL() {{
    DELETE_FROM("foo_table");
    WHERE("id = #{id}");
}};
        
        return sql.toString();
    }
}
