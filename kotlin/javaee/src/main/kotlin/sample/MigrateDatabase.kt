package sample

import javax.annotation.Resource
import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener
import javax.servlet.annotation.WebListener
import javax.sql.DataSource

import org.flywaydb.core.Flyway

@WebListener
class MigrateDatabase : ServletContextListener {
    
    @Resource(lookup="java:app/sample")
    lateinit private var ds : DataSource
    
    override fun contextInitialized(e: ServletContextEvent?) {
        val flyway = Flyway()
        
        flyway.setDataSource(this.ds)
        flyway.migrate()
    }
    
    override fun contextDestroyed(e: ServletContextEvent?) {
    }
}