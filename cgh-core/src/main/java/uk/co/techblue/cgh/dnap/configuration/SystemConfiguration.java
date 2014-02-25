package uk.co.techblue.cgh.dnap.configuration;

import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.PROPERTIES_PATH;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.SYSTEM_PROPERTIES;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.USER_PROPERTIES_PATH;
import uk.co.techblue.cgh.dnap.annotation.PersistProperties;
import uk.co.techblue.cgh.dnap.annotation.Property;
import uk.co.techblue.cgh.dnap.annotation.PersistProperties.ResourceBasePath;

// TODO: Auto-generated Javadoc
/**
 * The Class SystemConfiguration.
 * @author dheeraj
 */
@PersistProperties(defaultPath = PROPERTIES_PATH + SYSTEM_PROPERTIES, basePath=ResourceBasePath.USER_HOME, path = USER_PROPERTIES_PATH
+ SYSTEM_PROPERTIES)
public class SystemConfiguration implements IConfiguration {
	
	/** The watch directory. */
	@Property(name = "watch.folder.path")
	private String watchDirectory;
	
    /** The Reference watch directory. */
    @Property(name = "refwatch.folder.path")
    private String refWatchDirectory;

	/** The archive directory. */
	@Property(name = "archive.folder.path")
	private String archiveDirectory;
	
	/** The toggle state. */
	@Property(name = "toggle.state")
	private String toggleState;

	/**
	 * Gets the watch directory.
	 *
	 * @return the watch directory
	 */
	public String getWatchDirectory() {
		return watchDirectory;
	}

	/**
	 * Sets the watch directory.
	 *
	 * @param watchDirectory the new watch directory
	 */
	public void setWatchDirectory(String watchDirectory) {
		this.watchDirectory = watchDirectory;
	}

	/**
	 * Gets the archive directory.
	 *
	 * @return the archive directory
	 */
	public String getArchiveDirectory() {
		return archiveDirectory;
	}

	/**
	 * Sets the archive directory.
	 *
	 * @param archiveDirectory the new archive directory
	 */
	public void setArchiveDirectory(String archiveDirectory) {
		this.archiveDirectory = archiveDirectory;
	}

    /**
     * Gets the toggle state.
     *
     * @return the toggle state
     */
    public String getToggleState() {
        return toggleState;
    }

    /**
     * Sets the toggle state.
     *
     * @param toggleState the new toggle state
     */
    public void setToggleState(String toggleState) {
        this.toggleState = toggleState;
    }

    /**
     * Gets the ReferenceDataDirectory state.
     * 
     * @return refWatchDirectory
     */
    public String getRefWatchDirectory() {
        return refWatchDirectory;
    }

    // added by shiva
    /**
     * Sets the ReferenceDataDirectory
     * 
     * @param refWatchDirectory the new ReferenceDataDirectory
     */
    public void setRefWatchDirectory(String refWatchDirectory) {
        this.refWatchDirectory = refWatchDirectory;
    }

}
