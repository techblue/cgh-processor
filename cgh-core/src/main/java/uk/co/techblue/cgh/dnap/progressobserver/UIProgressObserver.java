package uk.co.techblue.cgh.dnap.progressobserver;

import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.ARCHIVE_DIR_PATH;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.WATCH_DIR_PATH;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import uk.co.techblue.cgh.dnap.annotation.Property;
import uk.co.techblue.cgh.dnap.configuration.SystemConfiguration;
import uk.co.techblue.cgh.dnap.exception.CGHProcessorException;
import uk.co.techblue.cgh.dnap.ui.ProgressConsole;
import uk.co.techblue.cgh.dnap.util.SignalProcessorHelper;

// TODO: Auto-generated Javadoc
/**
 * An asynchronous update interface for receiving notifications about UIProgress information as the UIProgress is constructed.
 * 
 * @author dheeraj
 */
public class UIProgressObserver implements ProgressObserver {

    /** The progress console. */
    private final ProgressConsole progressConsole;

    /**
     * This method is called when information about an UIProgress which was previously requested using an asynchronous interface
     * becomes available.
     * 
     * @param progressWindow the progress window
     */
    public UIProgressObserver(ProgressConsole progressWindow) {
        this.progressConsole = progressWindow;
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.co.techblue.cgh.dnap.progressobserver.ProgressObserver#publishProgressStart()
     */
    @Override
    public void publishProgressStart() {
        progressConsole.clear();
        progressConsole.insertText("Process Started...", LogLevel.INFO);
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.co.techblue.cgh.dnap.progressobserver.ProgressObserver#publishProgressComplete()
     */
    @Override
    public void publishProgressComplete() {
        archiveDataFiles();
        progressConsole.insertText("Process Completed Successfully.", LogLevel.INFO);
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.co.techblue.cgh.dnap.progressobserver.ProgressObserver#publishProgressInfo(java.lang.String)
     */
    @Override
    public void publishProgressInfo(final String message) {
        progressConsole.insertText(message, LogLevel.INFO);
    }

    /*
     * (non-Javadoc)
     * 
     * @see uk.co.techblue.cgh.dnap.progressobserver.ProgressObserver#publishProgressError(java.lang.String)
     */
    @Override
    public void publishProgressError(final String message) {
        progressConsole.insertText(message, LogLevel.ERROR);
    }

    /**
     * This method is called when information about an UIProgress which was previously requested using an asynchronous interface
     * becomes available.
     */
    private void archiveDataFiles() {
        String watchDirectoryPath = null;
        String archiveDirectoryPath = null;
        Properties props = null;
        try {
            props = SignalProcessorHelper.getProperties(SystemConfiguration.class);
        } catch (CGHProcessorException cghe) {
            publishProgressError("An error occurred while archiving the data files " + cghe.getMessage());
            cghe.printStackTrace();
        }
        if (props == null) {
            return;
        }
        for (final Field classField : SystemConfiguration.class.getDeclaredFields()) {
            if (classField.isAnnotationPresent(Property.class)) {
                final Property property = classField.getAnnotation(Property.class);
                final String propertyName = property.name();
                if (WATCH_DIR_PATH.equals(propertyName)) {
                    watchDirectoryPath = props.getProperty(WATCH_DIR_PATH);
                } else if (ARCHIVE_DIR_PATH.equals(propertyName)) {
                    archiveDirectoryPath = props.getProperty(ARCHIVE_DIR_PATH);
                }
            }
        }

        File watchDirectory = new File(watchDirectoryPath);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String destDirectoryName = df.format(new Date());

        File destDirectory = new File(archiveDirectoryPath, destDirectoryName);

        File[] dataFiles = watchDirectory.listFiles();

        for (File file : dataFiles) {
            try {
                FileUtils.moveFileToDirectory(file.getAbsoluteFile(), destDirectory, true);
            } catch (IOException e) {
                publishProgressError("An error occurred while archiving the data files " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

}
