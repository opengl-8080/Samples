package sample;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;

@WebListener
public class MigrateDatabase implements ServletContextListener {

    @Resource(lookup="java:app/sample")
    private DataSource ds;
    
    @Override
    public void contextInitialized(ServletContextEvent e) {
        Flyway flyway = new Flyway();
        
        flyway.setDataSource(this.ds);
        flyway.migrate();
    }

    @Override
    public void contextDestroyed(ServletContextEvent e) {}
}
