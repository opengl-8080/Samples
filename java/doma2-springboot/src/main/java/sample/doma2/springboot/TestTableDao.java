package sample.doma2.springboot;

import java.util.List;

import org.seasar.doma.AnnotateWith;
import org.seasar.doma.Annotation;
import org.seasar.doma.AnnotationTarget;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Dao
@AnnotateWith(annotations={
    @Annotation(target=AnnotationTarget.CLASS, type=Repository.class),
    @Annotation(target=AnnotationTarget.CONSTRUCTOR, type=Autowired.class)
})
public interface TestTableDao {

    @Select
    TestTable findById(int id);
    
    @Select
    List<TestTable> findAll();
    
    @Update
    int update(TestTable testTable);
}
