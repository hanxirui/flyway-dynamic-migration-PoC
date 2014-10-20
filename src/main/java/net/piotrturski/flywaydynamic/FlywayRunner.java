package net.piotrturski.flywaydynamic;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class FlywayRunner {

	public static JdbcTemplate createDb() {
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setUrl("jdbc:h2:mem:db");
		Flyway flyway = new Flyway();
		flyway.setDataSource(dataSource);
		flyway.setLocations();
		
		flyway.migrate();
		
		return new JdbcTemplate(dataSource);
	}
	
}
