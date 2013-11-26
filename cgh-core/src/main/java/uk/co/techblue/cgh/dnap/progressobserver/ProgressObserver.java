package uk.co.techblue.cgh.dnap.progressobserver;

// TODO: Auto-generated Javadoc
/**
 * An asynchronous update interface for receiving notifications about Progress information as the Progress is constructed.
 * @author dheeraj
 */
public interface ProgressObserver {

    /**
     * The Enum LogLevel.
     */
    public enum LogLevel {

        /** The info. */
        INFO,
        /** The error. */
        ERROR,
        /** The warning. */
        WARNING
    };

    /**
     * This method is called when information about an Progress which was previously requested using an asynchronous interface
     * becomes available on start of the process.
     */
    void publishProgressStart();

    /**
     * This method is called when information about an Progress which was previously requested using an asynchronous interface
     * becomes available on completion of the process.
     */
    void publishProgressComplete();

    /**
     * This method is called when information about an Progress which was previously requested using an asynchronous interface
     * becomes available.
     * 
     * @param message the message
     */
    void publishProgressInfo(final String message);

    /**
     * This method is called when error about an Progress which was previously requested using an asynchronous interface
     * becomes available.
     * 
     * @param message the message
     */
    void publishProgressError(final String message);

}
