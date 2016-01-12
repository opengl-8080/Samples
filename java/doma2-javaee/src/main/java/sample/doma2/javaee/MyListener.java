package sample.doma2.javaee;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;

@WebListener
public class MyListener implements ServletContextListener {

    @Resource(lookup="java:app/database")
    private DataSource ds;
    
    @Override
    public void contextInitialized(ServletContextEvent event) {
        Flyway flyway = new Flyway();
        flyway.setDataSource(this.ds);
        flyway.migrate();
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent event) {}

}
