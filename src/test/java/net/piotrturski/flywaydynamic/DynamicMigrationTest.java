package net.piotrturski.flywaydynamic;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.annotations.Test;

public class DynamicMigrationTest {

	JdbcTemplate jdbcTemplate = FlywayRunner.createDb();
	
	@Test
	public void should_fail_when_table_doesnt_exist() {
		
		catchException(jdbcTemplate).queryForMap("select * from nonexisting_table");
		
		assertThat(caughtException())
			.isInstanceOf(BadSqlGrammarException.class)
			.hasMessageContaining("Table \"NONEXISTING_TABLE\" not found");
	}
	
}
