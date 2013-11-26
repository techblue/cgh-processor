package uk.co.techblue.cgh.dnap.watcher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.techblue.cgh.dnap.configuration.GeneralConfiguration;
import uk.co.techblue.cgh.dnap.controllerthread.StartProcess;
import uk.co.techblue.cgh.dnap.dto.Attribute;
import uk.co.techblue.cgh.dnap.dto.Region;
import uk.co.techblue.cgh.dnap.exception.CGHProcessorException;
import uk.co.techblue.cgh.dnap.progressobserver.ProgressObserver;
import uk.co.techblue.cgh.dnap.signalprocessor.SignalExtractionProcessor;
import uk.co.techblue.cgh.dnap.util.SignalProcessorHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class WatchService.
 * 
 * @author dheeraj
 */
public class WatcherService {

    /** The Constant LOGGER. */
    private final static Logger logger = LoggerFactory.getLogger(WatcherService.class);

    /** The monitor. */
    private final FileAlterationMonitor monitor;

    /** The data files. */
    private List<String> dataFiles;

    /** The attributes. */
    private List<Attribute> attributes = null;

    /** The regions. */
    private List<Region> regions = null;

    /** The general properties. */
    private GeneralConfiguration generalProperties;

    /** The ui progress observer. */
    private ProgressObserver uiProgressObserver;

    /**
     * The main method.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {
    }

    /**
     * Instantiates a new watcher service.
     * 
     * @param pollingInterval the polling interval
     * @throws CGHProcessorException the nHS processor exception
     */
    public WatcherService(final long pollingInterval) throws CGHProcessorException {
        this.dataFiles = new ArrayList<String>();
        monitor = new FileAlterationMonitor(pollingInterval);
        this.generalProperties = SignalProcessorHelper.getConfigurationProperties(GeneralConfiguration.class);
    }

    /**
     * Start watch service.
     * 
     * @param watchDirectoryPath the watch directory path
     * @throws CGHProcessorException the nHS processor exception
     */
    public void startWatchService(final String watchDirectoryPath) throws CGHProcessorException {
        File directory = new File(watchDirectoryPath);
        uiProgressObserver.publishProgressInfo("Watching directory:  " + watchDirectoryPath);
        logger.info("Watching directory:  {}", watchDirectoryPath);
        if (!directory.exists()) {
            throw new RuntimeException("Directory not found: " + watchDirectoryPath);
        }

        logger.debug("Getting the file alteration listener");
        FileAlterationListener listener = getFileAlterationListener();

        FileAlterationObserver observer = new FileAlterationObserver(directory);

        observer.addListener(listener);
        monitor.addObserver(observer);
        try {
            logger.debug("Start File alteration monitor.");
            monitor.start();
        } catch (Exception exception) {
            throw new CGHProcessorException("Error occurred while starting watcher service for directory: '"
                    + watchDirectoryPath + "'", exception);
        }
    }

    /**
     * Register file alteration listener.
     * 
     * @return the file alteration listener adaptor
     */
    private FileAlterationListenerAdaptor getFileAlterationListener() {
        return new FileAlterationListenerAdaptor() {
            public void onFileCreate(File file) {
                logger.info("File {} created", file.getAbsolutePath());

                if (file.getName().equals(generalProperties.getAttribFileName())) {
                    try {
                        attributes = parseAttributes(file.getAbsolutePath());
                    } catch (CGHProcessorException nhse) {
                        throw new RuntimeException("", nhse);
                    }
                } else if (file.getName().equals(generalProperties.getRegionFileName())) {
                    try {
                        regions = parseRegions(file.getAbsolutePath());
                    } catch (CGHProcessorException nhse) {
                        throw new RuntimeException("", nhse);
                    }
                } else {
                    dataFiles.add(file.getAbsolutePath());
                }

                if (validateDataFiles() && !ApplicationWatcher.isThreadAlive()) {
                    StartProcess process = new StartProcess(new ArrayList<Attribute>(attributes), new ArrayList<String>(
                            dataFiles), new ArrayList<Region>(regions));
                    // process.setProgressObserverDelegate(getProgressObserverWrapper());
                    process.setProgressObserver(uiProgressObserver);
                    new Thread(process).start();
                    releaseResources();
                }
            }

            public void onFileDelete(File file) {
                logger.debug("File {} deleted", file.getAbsolutePath());
            }

            public void onDirectoryCreate(File directory) {
                logger.debug("Directory {} created", directory.getAbsolutePath());
            }

            public void onDirectoryChange(File directory) {
                logger.debug("Directory {} changed", directory.getAbsolutePath());
            }

            public void onDirectoryDelete(File directory) {
                logger.debug("Directory {} deleted", directory.getAbsolutePath());
            }

            public void onFileChange(File file) {
                logger.debug("File {} changed", file.getAbsolutePath());
            }

        };
    }

    // private ProgressObserverWrapper getProgressObserverWrapper() {
    // return new ProgressObserverWrapper();
    // }

    /**
     * Parses the regions.
     * 
     * @param absolutePath the absolute path
     * @return the list
     * @throws CGHProcessorException the nHS processor exception
     */
    private List<Region> parseRegions(final String absolutePath) throws CGHProcessorException {
        logger.info("Parsing {} ", absolutePath);
        uiProgressObserver.publishProgressInfo("Parsing " + absolutePath);
        return SignalExtractionProcessor.getRegions(absolutePath);
    }

    /**
     * Validate data files.
     * 
     * @return true, if successful
     */
    private boolean validateDataFiles() {
        logger.debug("Validating the number of data files in the watched directory with the entries in attrib.txt");
        List<String> finalDatafiles = null;
        if (attributes == null || attributes.isEmpty() || regions == null || regions.isEmpty() || dataFiles.isEmpty()
                || attributes.size() > dataFiles.size()) {
            return false;
        }
        finalDatafiles = new ArrayList<String>();
        for (Attribute attribute : attributes) {
            String arrayId = attribute.getArrayId();
            String prefix = StringUtils.substringBefore(arrayId, "_");
            String suffix = StringUtils.substring(arrayId, arrayId.length() - 3, arrayId.length());
            for (String dataFilePath : dataFiles) {
                String dataFileBaseName = FilenameUtils.getBaseName(dataFilePath);
                if (dataFileBaseName.startsWith(prefix) && dataFileBaseName.endsWith(suffix)) {
                    finalDatafiles.add(dataFilePath);
                    break;
                }
            }
        }
        dataFiles = finalDatafiles;
        return attributes.size() == dataFiles.size();
    }

    /**
     * Parses the attributes.
     * 
     * @param absolutePath the absolute path
     * @return the list
     * @throws CGHProcessorException the nHS processor exception
     */
    protected List<Attribute> parseAttributes(String absolutePath) throws CGHProcessorException {
        logger.info("Parsing {} ", absolutePath);
        uiProgressObserver.publishProgressInfo("Parsing " + absolutePath);
        return SignalExtractionProcessor.getAttributes(absolutePath);
    }

    /**
     * Stop.
     * 
     * @throws CGHProcessorException the nHS processor exception
     */
    public void stopWatchService() throws CGHProcessorException {
        if (monitor != null) {
            Iterable<FileAlterationObserver> observers = monitor.getObservers();
            if (observers != null) {
                logger.debug("Removing all file observers from file monitor.");
                for (FileAlterationObserver fileAlterationObserver : observers) {
                    monitor.removeObserver(fileAlterationObserver);
                }
            }
            try {
                logger.info("Stopping file monitor.");
                uiProgressObserver.publishProgressInfo("Stopping folder watcher service.");
                monitor.stop();
            } catch (Exception exception) {
                uiProgressObserver.publishProgressError("Error occurred while stopping the monitor. " + exception.getMessage());
                throw new CGHProcessorException("Error occurred while stopping the monitor.", exception);
            }
        }
    }

    /**
     * Release resources.
     */
    private void releaseResources() {
        attributes.clear();
        dataFiles.clear();
        regions.clear();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#finalize()
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        stopWatchService();
    }

    /**
     * Sets the progress observer.
     * 
     * @param uiProgressObserver the new progress observer
     */
    public void setProgressObserver(final ProgressObserver uiProgressObserver) {
        this.uiProgressObserver = uiProgressObserver;
    }

}
