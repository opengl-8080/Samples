package sample.springboot.doma2;

import org.seasar.doma.boot.autoconfigure.DomaConfigBuilder;
import org.seasar.doma.jdbc.JdbcLogger;
import org.seasar.doma.jdbc.UtilLoggingJdbcLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.logging.Level;

@SpringBootApplication
public class Doma2Application {

	public static void main(String[] args) {
		try (ConfigurableApplicationContext context = SpringApplication.run(Doma2Application.class, args)) {
			TestTableDao dao = context.getBean(TestTableDao.class);

			System.out.println(dao.findAll());

			TestTable foo = new TestTable();
			foo.name = "foo";
			dao.insert(foo);

			TestTable bar = new TestTable();
			bar.name = "bar";
			dao.insert(bar);

			System.out.println(dao.findAll());
		}
	}
	
	@Bean
	public JdbcLogger jdbcLogger() {
		return new UtilLoggingJdbcLogger(Level.FINE);
	}

	@Bean
	public DomaConfigBuilder domaConfigBuilder() {
		DomaConfigBuilder builder = new DomaConfigBuilder();
		builder.jdbcLogger(jdbcLogger());
		return builder;
	}
}
