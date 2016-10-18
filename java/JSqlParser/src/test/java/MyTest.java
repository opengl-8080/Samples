import net.sf.jsqlparser.expression.AllComparisonExpression;
import net.sf.jsqlparser.expression.AnalyticExpression;
import net.sf.jsqlparser.expression.AnyComparisonExpression;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.CastExpression;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.ExtractExpression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.HexValue;
import net.sf.jsqlparser.expression.IntervalExpression;
import net.sf.jsqlparser.expression.JdbcNamedParameter;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.JsonExpression;
import net.sf.jsqlparser.expression.KeepExpression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.MySQLGroupConcat;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.NumericBind;
import net.sf.jsqlparser.expression.OracleHierarchicalExpression;
import net.sf.jsqlparser.expression.OracleHint;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.RowConstructor;
import net.sf.jsqlparser.expression.SignedExpression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.TimeValue;
import net.sf.jsqlparser.expression.TimestampValue;
import net.sf.jsqlparser.expression.UserVariable;
import net.sf.jsqlparser.expression.WhenClause;
import net.sf.jsqlparser.expression.WithinGroupExpression;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseAnd;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseOr;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseXor;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import net.sf.jsqlparser.expression.operators.arithmetic.Division;
import net.sf.jsqlparser.expression.operators.arithmetic.Modulo;
import net.sf.jsqlparser.expression.operators.arithmetic.Multiplication;
import net.sf.jsqlparser.expression.operators.arithmetic.Subtraction;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.Matches;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.expression.operators.relational.RegExpMatchOperator;
import net.sf.jsqlparser.expression.operators.relational.RegExpMySQLOperator;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitorAdapter;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectVisitorAdapter;
import net.sf.jsqlparser.statement.select.SubSelect;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import sample.jsqlparser.MyExpressionVisitor;
import sample.jsqlparser.UnsupportedExpressionException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyTest {

    @Test
    public void 許可されない文法() throws Exception {
        notAccepted("sub query", "name = (select name from table1)");
        notAccepted("function", "name = to_date('2015-02-01', 'yyyy-MM-dd')");

    }

    @Test
    public void 許可された文法() throws Exception {
        accepted("=", "name = 'foo'");
        accepted("!=", "name != 'foo'");
        accepted("<>", "name <> 'foo'");
        accepted(">", "age > 20");
        accepted(">=", "age >= 20");
        accepted("<", "age < 10");
        accepted("<=", "age <= 10");
        accepted("LIKE", "name LIKE 'foo%'");
        accepted("NOT LIKE", "name NOT LIKE 'foo%'", " NOT name LIKE 'foo%'");
        accepted("IN", "name IN ('foo', 'bar')");
        accepted("NOT IN", "name NOT IN ('foo', 'bar')");
        accepted("AND", "name = 'foo' AND age <> 20");
        accepted("OR", "name = 'foo' OR age <> 20");
        accepted("AND OR", "name = 'foo' AND (name = 'bar' OR age = 20)");
        accepted("IS NULL", "name IS NULL");
        accepted("IS NOT NULL", "name IS NOT NULL");
    }

    private void accepted(String description, String sql, String expected) throws Exception {
        Expression expression = CCJSqlParserUtil.parseCondExpression(sql);

        MyExpressionVisitor visitor = new MyExpressionVisitor();

        expression.accept(visitor);

        Assertions.assertThat(visitor.toString()).as(description).isEqualTo(expected);
    }

    private void accepted(String description, String sql) throws Exception {
        accepted(description, sql, sql);
    }

    private void notAccepted(String description, String sql) throws Exception {
        Expression expression = CCJSqlParserUtil.parseCondExpression(sql);

        MyExpressionVisitor visitor = new MyExpressionVisitor();

        Assertions
            .assertThatThrownBy(() -> expression.accept(visitor))
            .as(description)
            .isInstanceOf(UnsupportedExpressionException.class);
    }

    @Test
    public void 条件式だけのパース() throws Exception {
        Expression expression = CCJSqlParserUtil.parseCondExpression("name = 'foo' and (id = 20 or value like 'a%'); select * from users");

        Set<String> expectedColumns = new HashSet<>();
        expectedColumns.add("name");
        expectedColumns.add("id");
        expectedColumns.add("value");

        expression.accept(new ExpressionVisitorAdapter() {

            @Override
            public void visit(Column column) {
                System.out.println(column);
                Assertions.assertThat(column.toString()).isIn(expectedColumns);
            }
        });

        System.out.println(expression);
        Assertions.assertThat(expression.toString()).isEqualTo("name = 'foo' AND (id = 20 OR value LIKE 'a%')");
    }

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
