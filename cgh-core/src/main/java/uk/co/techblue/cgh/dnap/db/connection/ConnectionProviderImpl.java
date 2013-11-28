package uk.co.techblue.cgh.dnap.db.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.jooq.ConnectionProvider;
import org.jooq.exception.DataAccessException;

import uk.co.techblue.cgh.dnap.configuration.DbConfiguration;
import uk.co.techblue.cgh.dnap.exception.CGHProcessorException;
import uk.co.techblue.cgh.dnap.util.SignalProcessorHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class ConnectionProviderImpl.
 * 
 * @author dheeraj
 */
public class ConnectionProviderImpl implements ConnectionProvider {

    /** The connection provider. */
    private static ConnectionProvider connectionProvider = null;

    /**
     * Instantiates a new connection provider impl.
     */
    private ConnectionProviderImpl() {
        if (connectionProvider != null) {
            throw new UnsupportedOperationException("Instantiation is only possible through getInstance() method.");
        }
    }

    /**
     * Gets the single instance of ConnectionProviderImpl.
     * 
     * @return single instance of ConnectionProviderImpl
     */
    public static ConnectionProvider getInstance() {
        if (connectionProvider == null) {
            connectionProvider = new ConnectionProviderImpl();
        }
        return connectionProvider;
    }

    /**
     * Creates the connection.
     * 
     * @return the connection
     * @throws DataAccessException the data access exception
     * @throws CGHProcessorException the cGH processor exception
     */
    private Connection createConnection() throws DataAccessException {
        DbConfiguration dbConfiguration = null;
        try {
            dbConfiguration = SignalProcessorHelper.getConfigurationProperties(DbConfiguration.class);
        } catch (CGHProcessorException cghe) {
            throw new DataAccessException("", cghe);
        }

        if (dbConfiguration == null || StringUtils.isBlank(dbConfiguration.getDriver())) {
            return null;
            // throw new
            // IllegalStateException("An error occurred while reading the application database configuration properties");
        }

        StringBuffer connectionUrlBuilder = new StringBuffer();
        connectionUrlBuilder.append("jdbc:mysql://");
        connectionUrlBuilder.append(dbConfiguration.getHost());
        connectionUrlBuilder.append(":");
        connectionUrlBuilder.append(dbConfiguration.getPort());
        connectionUrlBuilder.append("/");
        connectionUrlBuilder.append(dbConfiguration.getSchema());

        String connectionUrl = connectionUrlBuilder.toString();

        String username = dbConfiguration.getUsername();

        String password = dbConfiguration.getPassword();

        String driver = dbConfiguration.getDriver();

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException cnfe) {
            throw new DataAccessException("Not able to load database driver", cnfe);
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionUrl, username, password);
        } catch (SQLException sqle) {
            throw new DataAccessException("An error occurred while acquring the database connection", sqle);
        }
        return connection;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jooq.ConnectionProvider#acquire()
     */
    public Connection acquire() throws DataAccessException {
        return createConnection();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jooq.ConnectionProvider#release(java.sql.Connection)
     */
    public void release(Connection connection) throws DataAccessException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException sqle) {
            throw new DataAccessException("Error occurred while releasing the database connection.", sqle);
        }

    }
}
