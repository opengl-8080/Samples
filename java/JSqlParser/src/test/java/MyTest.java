import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitorAdapter;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectVisitorAdapter;
import org.junit.Test;

import java.util.List;

public class MyTest {

    @Test
    public void helloWorld() throws Exception {
        Statement statement = CCJSqlParserUtil.parse("select id, name, value from table1" +
                " where name = 'foo' and id < 30 and value = 40");

        statement.accept(new StatementVisitorAdapter() {

            @Override
            public void visit(Select select) {

                select.getSelectBody().accept(new SelectVisitorAdapter() {

                    @Override
                    public void visit(PlainSelect plainSelect) {
                        List<SelectItem> selectItems = plainSelect.getSelectItems();
                        System.out.println("selectItems = " + selectItems);

                        FromItem fromItem = plainSelect.getFromItem();
                        System.out.println("fromItem = " + fromItem);

                        Expression where = plainSelect.getWhere();

                        where.accept(new ExpressionVisitorAdapter() {

                            @Override
                            public void visit(AndExpression expr) {
                                System.out.println("AndExpression = " + expr);
                                System.out.println("left = " + expr.getLeftExpression());
                                System.out.println("right = " + expr.getRightExpression());
                            }
                        });
                    }
                });
            }
        });
    }
}
