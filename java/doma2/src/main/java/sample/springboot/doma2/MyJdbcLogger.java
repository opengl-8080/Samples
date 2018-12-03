package sample.springboot.doma2;

import org.seasar.doma.jdbc.Sql;
import org.seasar.doma.jdbc.UtilLoggingJdbcLogger;

import java.util.function.Supplier;
import java.util.logging.Level;

public class MyJdbcLogger extends UtilLoggingJdbcLogger {
    
    public MyJdbcLogger() {
        super(Level.FINE);
    }

    @Override
    protected void logSql(String callerClassName, String callerMethodName, Sql<?> sql, Level level, Supplier<String> messageSupplier) {
        super.logSql(callerClassName, callerMethodName, sql, Level.INFO, messageSupplier);
    }
}
