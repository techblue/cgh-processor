package uk.co.techblue.cgh.dnap.db;

import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.DB_CHANGESET;

import java.sql.Connection;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import org.jooq.ConnectionProvider;

import uk.co.techblue.cgh.dnap.db.connection.ConnectionProviderImpl;
import uk.co.techblue.cgh.dnap.exception.CGHProcessorException;

/**
 * The Class DatabaseSchemaSynchronizer.
 * 
 * @author dheeraj
 * 
 */
public class DatabaseSchemaSynchronizer {

    /** The Constant connectionProvider. */
    private static final ConnectionProvider connectionProvider = ConnectionProviderImpl.getInstance();
    
    /**
     * Update database schema.
     * @throws CGHProcessorException 
     */
    public static void updateDatabaseSchema() throws CGHProcessorException {
        Liquibase liquibase = null;
        Connection connection = null;
        Database database = null;
        connection = connectionProvider.acquire();
        try {
            if(connection == null) {
                return;
            }
            
            database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

            liquibase = new Liquibase(DB_CHANGESET, new ClassLoaderResourceAccessor(), database);

            liquibase.update(null);
        } catch (LiquibaseException le) {
            throw new CGHProcessorException("Error occurred while updating the schema", le);
        } finally {
            connectionProvider.release(connection);
        }
    }

}
