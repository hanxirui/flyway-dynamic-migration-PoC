package net.piotrturski.flywaydynamic;

import java.sql.Connection;
import java.util.Collection;
import java.util.Collections;

import javax.sql.DataSource;

import org.flywaydb.core.api.MigrationType;
import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.api.resolver.MigrationResolver;
import org.flywaydb.core.api.resolver.ResolvedMigration;
import org.flywaydb.core.internal.dbsupport.DbSupport;
import org.flywaydb.core.internal.dbsupport.DbSupportFactory;
import org.flywaydb.core.internal.resolver.ResolvedMigrationImpl;
import org.flywaydb.core.internal.util.jdbc.JdbcUtils;

public class DynamicSqlMigrationResolver implements MigrationResolver {

	private DbSupport dbSupport;
	
	public DynamicSqlMigrationResolver(DataSource dataSource) {
		Connection connection = JdbcUtils.openConnection(dataSource);//FIXME connection leak
        dbSupport = DbSupportFactory.createDbSupport(connection, false);
	}

	public Collection<ResolvedMigration> resolveMigrations() {
		String dynamicContent = 
				"create table table_from_dynamic_migration (sample_column varchar2);" +
				"insert into  table_from_dynamic_migration values ('sample_content');";

        ResolvedMigrationImpl resolvedMigration = new ResolvedMigrationImpl();
        resolvedMigration.setExecutor(
            new DynamicSqlMigrationExecutor(dynamicContent, dbSupport));
        resolvedMigration.setVersion(MigrationVersion.fromVersion("1.4"));
        resolvedMigration.setType(MigrationType.CUSTOM);
        resolvedMigration.setDescription("dynamic description");
        resolvedMigration.setScript("generated script");

        return Collections.singleton((ResolvedMigration)resolvedMigration);
	}

}
