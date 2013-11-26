package uk.co.techblue.cgh.dnap.configuration;

import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.*;
import uk.co.techblue.cgh.dnap.annotation.PersistProperties;
import uk.co.techblue.cgh.dnap.annotation.Property;
import uk.co.techblue.cgh.dnap.annotation.PersistProperties.ResourceBasePath;

/**
 * The Class DbConfiguration.
 * 
 * @author dheeraj
 */
@PersistProperties(defaultPath = PROPERTIES_PATH+DATABASE_PROPERTIES, basePath = ResourceBasePath.USER_HOME, path = USER_PROPERTIES_PATH + DATABASE_PROPERTIES)
public class DbConfiguration implements IConfiguration {

    /** The host. */
    @Property(name = "db.host", displayName = "Server address", description = "Server address of the database")
    private String host;

    /** The port. */
    @Property(name = "db.port", displayName = "Server port", description = "Server post of the database")
    private String port;

    /** The schema. */
    @Property(name = "db.schema", displayName = "Schema name", editable = false, description = "Name of database")
    private String schema;

    /** The username. */
    @Property(name = "db.username", displayName = "Username", description = "Username to connect to database server")
    private String username;

    /** The password. */
    @Property(name = "db.password", displayName = "Password", description = "Password to connect to database server")
    private String password;

    /** The driver. */
    @Property(name = "db.driver", displayName = "Driver", editable = false, description = "Database driver(MySQL)")
    private String driver;

    /**
     * Gets the host.
     * 
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * Sets the host.
     * 
     * @param host the new host
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Gets the port.
     * 
     * @return the port
     */
    public String getPort() {
        return port;
    }

    /**
     * Sets the port.
     * 
     * @param port the new port
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * Gets the schema.
     * 
     * @return the schema
     */
    public String getSchema() {
        return schema;
    }

    /**
     * Sets the schema.
     * 
     * @param schema the new schema
     */
    public void setSchema(String schema) {
        this.schema = schema;
    }

    /**
     * Gets the username.
     * 
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     * 
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password.
     * 
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     * 
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the driver.
     * 
     * @return the driver
     */
    public String getDriver() {
        return driver;
    }

    /**
     * Sets the driver.
     * 
     * @param driver the new driver
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

}
