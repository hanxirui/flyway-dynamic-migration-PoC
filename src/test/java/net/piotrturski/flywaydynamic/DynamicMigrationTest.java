package net.piotrturski.flywaydynamic;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.MapEntry.entry;

import java.util.Map;

import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.annotations.Test;

public class DynamicMigrationTest {

	JdbcTemplate jdbcTemplate = FlywayRunner.createDb();
	
	@Test
	public void should_see_table_created_by_dynamic_migration() {
		
		Map<String, Object> map = jdbcTemplate.queryForMap("select * from table_from_dynamic_migration");
		
		assertThat(map).containsExactly(entry("SAMPLE_COLUMN", "sample_content"));
	}
	
	@Test
	public void should_fail_when_table_doesnt_exist() {
		
		catchException(jdbcTemplate).queryForMap("select * from nonexisting_table");
		
		assertThat(caughtException())
			.isInstanceOf(BadSqlGrammarException.class)
			.hasMessageContaining("Table \"NONEXISTING_TABLE\" not found");
	}
	
}
