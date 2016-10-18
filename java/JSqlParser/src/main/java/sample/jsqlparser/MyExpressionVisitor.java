package sample.jsqlparser;

import net.sf.jsqlparser.expression.AllComparisonExpression;
import net.sf.jsqlparser.expression.AnalyticExpression;
import net.sf.jsqlparser.expression.AnyComparisonExpression;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.CastExpression;
import net.sf.jsqlparser.expression.ExpressionVisitorAdapter;
import net.sf.jsqlparser.expression.ExtractExpression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.IntervalExpression;
import net.sf.jsqlparser.expression.JdbcNamedParameter;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.JsonExpression;
import net.sf.jsqlparser.expression.KeepExpression;
import net.sf.jsqlparser.expression.MySQLGroupConcat;
import net.sf.jsqlparser.expression.NumericBind;
import net.sf.jsqlparser.expression.OracleHierarchicalExpression;
import net.sf.jsqlparser.expression.OracleHint;
import net.sf.jsqlparser.expression.RowConstructor;
import net.sf.jsqlparser.expression.SignedExpression;
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
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import net.sf.jsqlparser.expression.operators.relational.Matches;
import net.sf.jsqlparser.expression.operators.relational.RegExpMatchOperator;
import net.sf.jsqlparser.expression.operators.relational.RegExpMySQLOperator;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.util.deparser.ExpressionDeParser;

public class MyExpressionVisitor extends ExpressionDeParser {

    private StringBuilder sb = new StringBuilder();

    public MyExpressionVisitor() {
        this.setBuffer(this.sb);
    }

    @Override
    public String toString() {
        return this.sb.toString();
    }

    @Override
    public void visit(Function function) {
        throw new UnsupportedExpressionException(function.toString());
    }

    @Override
    public void visit(SignedExpression signedExpression) {
        throw new UnsupportedExpressionException(signedExpression.toString());
    }

    @Override
    public void visit(JdbcParameter jdbcParameter) {
        throw new UnsupportedExpressionException(jdbcParameter.toString());
    }

    @Override
    public void visit(JdbcNamedParameter jdbcNamedParameter) {
        throw new UnsupportedExpressionException(jdbcNamedParameter.toString());
    }

    @Override
    public void visit(Addition addition) {
        throw new UnsupportedExpressionException(addition.toString());
    }

    @Override
    public void visit(Division division) {
        throw new UnsupportedExpressionException(division.toString());
    }

    @Override
    public void visit(Multiplication multiplication) {
        throw new UnsupportedExpressionException(multiplication.toString());
    }

    @Override
    public void visit(Subtraction subtraction) {
        throw new UnsupportedExpressionException(subtraction.toString());
    }

    @Override
    public void visit(SubSelect subSelect) {
        throw new UnsupportedExpressionException(subSelect.toString());
    }

    @Override
    public void visit(CaseExpression caseExpression) {
        throw new UnsupportedExpressionException(caseExpression.toString());
    }

    @Override
    public void visit(WhenClause whenClause) {
        throw new UnsupportedExpressionException(whenClause.toString());
    }

    @Override
    public void visit(ExistsExpression existsExpression) {
        throw new UnsupportedExpressionException(existsExpression.toString());
    }

    @Override
    public void visit(AllComparisonExpression allComparisonExpression) {
        throw new UnsupportedExpressionException(allComparisonExpression.toString());
    }

    @Override
    public void visit(AnyComparisonExpression anyComparisonExpression) {
        throw new UnsupportedExpressionException(anyComparisonExpression.toString());
    }

    @Override
    public void visit(Concat concat) {
        throw new UnsupportedExpressionException(concat.toString());
    }

    @Override
    public void visit(Matches matches) {
        throw new UnsupportedExpressionException(matches.toString());
    }

    @Override
    public void visit(BitwiseAnd bitwiseAnd) {
        throw new UnsupportedExpressionException(bitwiseAnd.toString());
    }

    @Override
    public void visit(BitwiseOr bitwiseOr) {
        throw new UnsupportedExpressionException(bitwiseOr.toString());
    }

    @Override
    public void visit(BitwiseXor bitwiseXor) {
        throw new UnsupportedExpressionException(bitwiseXor.toString());
    }

    @Override
    public void visit(CastExpression cast) {
        throw new UnsupportedExpressionException(cast.toString());
    }

    @Override
    public void visit(Modulo modulo) {
        throw new UnsupportedExpressionException(modulo.toString());
    }

    @Override
    public void visit(AnalyticExpression aexpr) {
        throw new UnsupportedExpressionException(aexpr.toString());
    }

    @Override
    public void visit(WithinGroupExpression wgexpr) {
        throw new UnsupportedExpressionException(wgexpr.toString());
    }

    @Override
    public void visit(ExtractExpression eexpr) {
        throw new UnsupportedExpressionException(eexpr.toString());
    }

    @Override
    public void visit(IntervalExpression iexpr) {
        throw new UnsupportedExpressionException(iexpr.toString());
    }

    @Override
    public void visit(OracleHierarchicalExpression oexpr) {
        throw new UnsupportedExpressionException(oexpr.toString());

    }

    @Override
    public void visit(RegExpMatchOperator rexpr) {
        throw new UnsupportedExpressionException(rexpr.toString());

    }

    @Override
    public void visit(JsonExpression jsonExpr) {

        throw new UnsupportedExpressionException(jsonExpr.toString());
    }

    @Override
    public void visit(RegExpMySQLOperator regExpMySQLOperator) {

        throw new UnsupportedExpressionException(regExpMySQLOperator.toString());
    }

    @Override
    public void visit(UserVariable var) {

        throw new UnsupportedExpressionException(var.toString());
    }

    @Override
    public void visit(NumericBind bind) {

        throw new UnsupportedExpressionException(bind.toString());
    }

    @Override
    public void visit(KeepExpression aexpr) {

        throw new UnsupportedExpressionException(aexpr.toString());
    }

    @Override
    public void visit(MySQLGroupConcat groupConcat) {

        throw new UnsupportedExpressionException(groupConcat.toString());
    }

    @Override
    public void visit(RowConstructor rowConstructor) {

        throw new UnsupportedExpressionException(rowConstructor.toString());
    }

    @Override
    public void visit(OracleHint hint) {

        throw new UnsupportedExpressionException(hint.toString());
    }
}
