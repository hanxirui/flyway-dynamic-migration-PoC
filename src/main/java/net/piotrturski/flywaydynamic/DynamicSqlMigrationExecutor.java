package net.piotrturski.flywaydynamic;

import java.sql.Connection;
import java.sql.SQLException;

import org.flywaydb.core.api.resolver.MigrationExecutor;
import org.flywaydb.core.internal.dbsupport.DbSupport;
import org.flywaydb.core.internal.dbsupport.JdbcTemplate;
import org.flywaydb.core.internal.dbsupport.SqlScript;

public class DynamicSqlMigrationExecutor implements MigrationExecutor {

	private DbSupport dbSupport;
	private String sqlContent;

	public DynamicSqlMigrationExecutor(String sqlContent, DbSupport dbSupport) {
		this.sqlContent = sqlContent;
		this.dbSupport = dbSupport;
	}

	public void execute(Connection connection) throws SQLException {
		SqlScript sqlScript = new SqlScript(sqlContent, dbSupport);
		sqlScript.execute(new JdbcTemplate(connection, 0));
	}

	public boolean executeInTransaction() {
		return true;
	}

}
