package uk.co.techblue.cgh.dnap.controllerthread;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.techblue.cgh.dnap.db.connection.ConnectionProviderImpl;
import uk.co.techblue.cgh.dnap.dto.Attribute;
import uk.co.techblue.cgh.dnap.dto.FeatureExtractorParameters;
import uk.co.techblue.cgh.dnap.dto.FeatureExtractorStatistics;
import uk.co.techblue.cgh.dnap.dto.Region;
import uk.co.techblue.cgh.dnap.dto.SignalFeatures;
import uk.co.techblue.cgh.dnap.exception.CGHProcessorException;
import uk.co.techblue.cgh.dnap.progressobserver.ProgressObserver;
import uk.co.techblue.cgh.dnap.services.SignalProcessorService;
import uk.co.techblue.cgh.dnap.services.impl.SignalProcessorServiceImpl;
import uk.co.techblue.cgh.dnap.signalprocessor.SignalExtractionProcessor;
import uk.co.techblue.cgh.dnap.tables.pojos.Baselineaverages;
import uk.co.techblue.cgh.dnap.tables.pojos.Regionintensity;
import uk.co.techblue.cgh.dnap.tables.records.RegionRecord;
import uk.co.techblue.cgh.dnap.tables.records.RegionintensityRecord;
import uk.co.techblue.cgh.dnap.tables.records.SignalRecord;
import uk.co.techblue.cgh.dnap.util.SignalProcessorHelper;
import uk.co.techblue.cgh.dnap.watcher.ApplicationWatcher;

/**
 * The Class StartProcess.
 * 
 * @author dheeraj
 */
public class StartProcess implements Runnable {

    /** The data files. */
    private final List<String> dataFiles;

    /** The attributes. */
    private final List<Attribute> attributes;

    private final List<Region> regions;

    /** The service. */
    private SignalProcessorService service = null;

    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /** The Constant connectionProvider. */
    private final ConnectionProvider connectionProvider = ConnectionProviderImpl.getInstance();

    // private ProgressObserverWrapper progressObserverWrapper = null;

    private ProgressObserver uiProgressObserver = null;

    /**
     * Instantiates a new start process.
     * 
     * @param attributes the attributes
     * @param files the files
     */
    public StartProcess(final List<Attribute> attributes, final List<String> files, final List<Region> regions) {
        this.attributes = attributes;
        this.dataFiles = files;
        this.service = new SignalProcessorServiceImpl();
        this.regions = regions;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    public void run() {
        uiProgressObserver.publishProgressStart();
        try {
            ApplicationWatcher.setThreadAlive(true);
            processDataFiles();
        } catch (CGHProcessorException cghe) {
            uiProgressObserver.publishProgressError(cghe.getLocalizedMessage());
            throw new RuntimeException("", cghe);
        } finally {
            ApplicationWatcher.setThreadAlive(false);
        }
        uiProgressObserver.publishProgressComplete();
    }

    /**
     * Read data files.
     * 
     * @throws CGHProcessorException
     */
    private void processDataFiles() throws CGHProcessorException {
        logger.info("Acquiring database connection.");
        uiProgressObserver.publishProgressInfo("Acquiring database connection.");
        Connection connection = null;
        try{
            connection = connectionProvider.acquire();
        } catch(DataAccessException dae) {
            uiProgressObserver.publishProgressError("An error occurred while acquiring database connection, Please check the log file.");
        }
        try {
            connection.setAutoCommit(false);
        } catch (SQLException sqle) {
            throw new CGHProcessorException("Error occurred while setting the connection state", sqle);
        }
        final DSLContext obj = DSL.using(connection, SQLDialect.MYSQL);
        final Configuration configuration = obj.configuration();
        saveRegions(configuration, regions);
        logger.debug("Getting all the regions from database.");
        List<RegionRecord> regionRecords = service.getRegions(configuration);

        try {
            logger.info("Reading data files...");
            uiProgressObserver.publishProgressInfo("Reading data files...");
            for (final String dataFilePath : dataFiles) {
                processArraysAndSignals(dataFilePath, configuration);
                auditDataFile(configuration, dataFilePath);
            }
            processRegionIntensities(configuration, regionRecords);
            processBaselineAverages(configuration, regionRecords);
            processZScores(configuration);
            truncateRegions(configuration);
            updateAudit(configuration);
            connection.commit();
        } catch (SQLException sqlexception) {
            try {
                connection.rollback();
            } catch (SQLException sqle) {
                logger.error("Error occurred while rolling back the changes.", sqle);
            }
            throw new CGHProcessorException("Error occurred while committing the data to database", sqlexception);
        } finally {
            connectionProvider.release(connection);
        }
    }

    private void updateAudit(final Configuration configuration) {
        service.updateAudit(configuration);
    }

    private void auditDataFile(final Configuration configuration, final String dataFilePath) {
        String dataFileName = FilenameUtils.getBaseName(dataFilePath);
        logger.info("Auditing data file '{}'", dataFileName);
        service.auditDataFile(configuration, dataFilePath);

    }

    private void truncateRegions(final Configuration configuration) {
        logger.info("Truncating regions table");
        uiProgressObserver.publishProgressInfo("Truncating regions table");
        service.truncateRegions(configuration);
    }

    private void saveRegions(final Configuration configuration, final List<Region> regions) {
        service.saveRegions(configuration, regions);
    }

    /**
     * Checks if is data file exist.
     * 
     * @param dataFilePath the data file path
     * @return true, if is data file exist
     */
    @SuppressWarnings("unused")
    private boolean isDataFileExist(final String dataFilePath) {
        for (final Attribute attribute : attributes) {
            String arrayId = attribute.getArrayId();
            String prefix = StringUtils.substringBefore(arrayId, "_");
            String suffix = StringUtils.substring(arrayId, arrayId.length() - 3, arrayId.length());
            String dataFileBaseName = FilenameUtils.getBaseName(dataFilePath);
            if (dataFileBaseName.startsWith(prefix) && dataFileBaseName.endsWith(suffix)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Read data file.
     * 
     * @param dataFilePath the data file path
     * @param configuration the configuration
     * @throws CGHProcessorException
     */
    private void processArraysAndSignals(final String dataFilePath, final Configuration configuration)
            throws CGHProcessorException {
        logger.debug("Reading tab delimited text data file: {}", dataFilePath);
        logger.debug("Reading FE parameters from tab delimited text data file: {}", dataFilePath);
        FeatureExtractorParameters feParams = SignalExtractionProcessor.getFEParams(dataFilePath);

        logger.debug("Get short array id from feature extractor barcode {}", feParams.getFeatureExtractorBarcode());
        try {
            feParams.setShortArrayId(SignalProcessorHelper.getFEShortArrayId(feParams.getFeatureExtractorBarcode()));
        } catch (CGHProcessorException cghe) {
            throw cghe;
        }

        logger.info("Reading FE Stats from tab delimited text data file: {}", dataFilePath);
        uiProgressObserver.publishProgressInfo("Reading FE Stats from tab delimited text data file: " + dataFilePath);
        FeatureExtractorStatistics feStats = SignalExtractionProcessor.getFEStats(dataFilePath);

        logger.info("Saving FE Params and FE Stats in the Array table in database for data file {}", dataFilePath);
        uiProgressObserver.publishProgressInfo("Saving FE Params and FE Stats in the Array table in database for data file "
                + dataFilePath);
        saveArray(feParams, feStats, configuration);

        logger.debug("Reading FE Features from tab delimited text data file: {}", dataFilePath);
        List<SignalFeatures> signalFeatures = SignalExtractionProcessor.getFEFeatures(dataFilePath);

        logger.debug("parsing systematic name into three parts, chromosome, start position and stop position.");
        for (final SignalFeatures feature : signalFeatures) {
            SignalProcessorHelper.processSystematicName(feature);
        }

        logger.info("Saving FE Features in the Signal table in database for data file {}", dataFilePath);
        uiProgressObserver.publishProgressInfo("Saving FE Features in the Signal table in database for data file "
                + dataFilePath);
        saveSignals(feParams.getFeatureExtractorBarcode(), signalFeatures, configuration);
    }

    /**
     * Save array.
     * 
     * @param feParams the fe params
     * @param feStats the fe stats
     * @param configuration the configuration
     */
    private void saveArray(final FeatureExtractorParameters feParams, final FeatureExtractorStatistics feStats,
            final Configuration configuration) {
        service.saveArray(configuration, feParams, feStats);
    }

    /**
     * Save signals.
     * 
     * @param featureExtractorBarcode the feature extractor barcode
     * @param signalFeatures the signal features
     * @param configuration the configuration
     */
    private void saveSignals(final String featureExtractorBarcode, final List<SignalFeatures> signalFeatures,
            final Configuration configuration) {
        service.saveSignals(configuration, featureExtractorBarcode, signalFeatures);
    }

    private void processRegionIntensities(final Configuration configuration, final List<RegionRecord> regionRecords) {
        logger.info("Processing Region intensities...");
        uiProgressObserver.publishProgressInfo("Processing Region intensities...");

        logger.debug("Getting all distinct feature extract bar codes from database.");
        List<String> featureBarcodes = service.getDistinctFeatureBarcodes(configuration);

        logger.info("Saving Region intensities in the database.");
        uiProgressObserver.publishProgressInfo("Saving Region intensities in the database.");

        for (String feBarcode : featureBarcodes) {
            List<Regionintensity> regionIntensities = new ArrayList<Regionintensity>();
            for (RegionRecord regionRecord : regionRecords) {
                Regionintensity regionIntensity = processRegionIntensity(configuration, regionRecord, feBarcode);
                if (regionIntensity != null) {
                    regionIntensities.add(regionIntensity);
                }
            }
            logger.info("Saving region intensities for feature extractor barcode, '{}'", feBarcode);
            uiProgressObserver.publishProgressInfo("Saving region intensities for feature extractor barcode, " + feBarcode);
            service.saveRegionIntensities(configuration, regionIntensities);
        }
    }

    private Regionintensity processRegionIntensity(final Configuration configuration, final RegionRecord regionRecord,
            final String featureExtractorBarcode) {
        Regionintensity regionIntensity = null;
        logger.debug(
                "Getting signal data wrt to feature extractor barcode, '{}' and Region[chromosome: {}, start postition: {}, stop postition: {}]",
                featureExtractorBarcode, regionRecord.getChromosome(), regionRecord.getStartposition(),
                regionRecord.getStopposition());
        List<SignalRecord> signalRecords = service.getSignalData(configuration, regionRecord, featureExtractorBarcode);
        if (signalRecords != null && !signalRecords.isEmpty()) {
            regionIntensity = new Regionintensity();
            List<Double> rProcessedSignals = new ArrayList<Double>();
            List<Double> gProcessedSignals = new ArrayList<Double>();
            List<Double> logRatios = new ArrayList<Double>();
            for (SignalRecord signalRecord : signalRecords) {
                gProcessedSignals.add(signalRecord.getGprocessedsignal());
                rProcessedSignals.add(signalRecord.getRprocessedsignal());
                logRatios.add(signalRecord.getLogratio());
            }

            double gProcessedSignalsMean = SignalProcessorHelper.calculateMean(ArrayUtils.toPrimitive(gProcessedSignals
                    .toArray(new Double[0])));
            logger.debug("Calculated mean for green processed signal is: {}", gProcessedSignalsMean);

            double rProcessedSignalsMean = SignalProcessorHelper.calculateMean(ArrayUtils.toPrimitive(rProcessedSignals
                    .toArray(new Double[0])));
            logger.debug("Calculated mean for red processed signal is: {}", rProcessedSignalsMean);

            double logRatioMean = SignalProcessorHelper.calculateMean(ArrayUtils.toPrimitive(logRatios.toArray(new Double[0])));
            logger.debug("Calculated mean for log ratio is: {}", logRatioMean);

            double gProcessedSignalsMedian = SignalProcessorHelper.calculateMedian(ArrayUtils.toPrimitive(gProcessedSignals
                    .toArray(new Double[0])));
            logger.debug("Calculated median for green processed signal is: {}", gProcessedSignalsMedian);

            double rProcessedSignalsMedian = SignalProcessorHelper.calculateMedian(ArrayUtils.toPrimitive(rProcessedSignals
                    .toArray(new Double[0])));
            logger.debug("Calculated median for red processed signal is: {}", rProcessedSignalsMedian);

            double logRatioMedian = SignalProcessorHelper.calculateMedian(ArrayUtils.toPrimitive(logRatios
                    .toArray(new Double[0])));
            logger.debug("Calculated median for log ratio is: {}", logRatioMedian);

            regionIntensity.setChromosome(regionRecord.getChromosome());
            regionIntensity.setFeatureextractorBarcode(featureExtractorBarcode);
            regionIntensity.setMeangreensignal(gProcessedSignalsMean);
            regionIntensity.setMeanlogratio(logRatioMean);
            regionIntensity.setMeanredsignal(rProcessedSignalsMean);
            regionIntensity.setMediangreensignal(gProcessedSignalsMedian);
            regionIntensity.setMedianlogratio(logRatioMedian);
            regionIntensity.setMedianredsignal(rProcessedSignalsMedian);
            regionIntensity.setRegionid(regionRecord.getRegionid().longValue());
            regionIntensity.setStartposition(regionRecord.getStartposition());
            regionIntensity.setStopposition(regionRecord.getStopposition());
        }
        return regionIntensity;
    }

    private void processBaselineAverages(final Configuration configuration, final List<RegionRecord> regionRecords) {
        logger.info("Processing baseline averages on the data.");
        uiProgressObserver.publishProgressInfo("Processing baseline averages on the data.");
        List<Baselineaverages> baselineAverages = new ArrayList<Baselineaverages>();
        for (RegionRecord regionRecord : regionRecords) {
            logger.debug(
                    "Getting all region intensities for region:, [chromosome: {}, start postition: {}, stop position: {}]",
                    regionRecord.getChromosome(), regionRecord.getStartposition(), regionRecord.getStopposition());

            List<RegionintensityRecord> regionIntensityRecords = service.getRegionIntensities(configuration, regionRecord);
            Baselineaverages baselineAverage = processBaselineAverage(configuration, regionIntensityRecords);
            if (baselineAverage != null) {
                baselineAverages.add(baselineAverage);
            }
        }
        logger.info("Saving calculated baseline averages in the database.");
        uiProgressObserver.publishProgressInfo("Saving calculated baseline averages in the database.");
        service.saveBaselineAverages(configuration, baselineAverages);
    }

    private Baselineaverages processBaselineAverage(final Configuration configuration,
            final List<RegionintensityRecord> regionIntensityRecords) {

        Baselineaverages baselineAverage = null;

        if (regionIntensityRecords == null || regionIntensityRecords.isEmpty()) {
            return baselineAverage;
        }

        List<Double> meanGreenSignals = new ArrayList<Double>();
        List<Double> meanRedSignals = new ArrayList<Double>();
        List<Double> meanLogRatios = new ArrayList<Double>();
        List<Double> medianGreenSignals = new ArrayList<Double>();
        List<Double> medianRedSignals = new ArrayList<Double>();
        List<Double> medianLogRatios = new ArrayList<Double>();

        baselineAverage = new Baselineaverages();

        for (RegionintensityRecord regionIntensityRecord : regionIntensityRecords) {

            meanGreenSignals.add(regionIntensityRecord.getMeangreensignal());

            meanRedSignals.add(regionIntensityRecord.getMeanredsignal());

            meanLogRatios.add(regionIntensityRecord.getMeanlogratio());

            medianGreenSignals.add(regionIntensityRecord.getMediangreensignal());

            medianRedSignals.add(regionIntensityRecord.getMedianredsignal());

            medianLogRatios.add(regionIntensityRecord.getMedianlogratio());

            baselineAverage.setChromosome(regionIntensityRecord.getChromosome());

            baselineAverage.setRegionid(regionIntensityRecord.getRegionid());

            baselineAverage.setStartposition(regionIntensityRecord.getStartposition());

            baselineAverage.setStopposition(regionIntensityRecord.getStopposition());
        }

        double bMeanGreenSignal = SignalProcessorHelper.calculateMean(ArrayUtils.toPrimitive(meanGreenSignals
                .toArray(new Double[0])));
        logger.debug("Calculate baseline mean for green signals: {}", bMeanGreenSignal);

        double bMedianGreenSignal = SignalProcessorHelper.calculateMedian(ArrayUtils.toPrimitive(medianGreenSignals
                .toArray(new Double[0])));
        logger.debug("Calculate baseline median for green signals: {}", bMedianGreenSignal);

        double bMeanGreenSignalSD = SignalProcessorHelper.calculateMeanSD(ArrayUtils.toPrimitive(meanGreenSignals
                .toArray(new Double[0])));
        logger.debug("Calculate baseline mean standard deviation for green signals: {}", bMeanGreenSignalSD);

        double bMedianGreenSignalSD = SignalProcessorHelper.calculateMedianSD(ArrayUtils.toPrimitive(medianGreenSignals
                .toArray(new Double[0])));
        logger.debug("Calculate baseline median standard deviation for green signals: {}", bMedianGreenSignalSD);
        
        

        double bMeanRedSignal = SignalProcessorHelper.calculateMean(ArrayUtils.toPrimitive(meanRedSignals
                .toArray(new Double[0])));
        logger.debug("Calculate baseline mean for red signals: {}", bMeanRedSignal);

        double bMedianRedSignal = SignalProcessorHelper.calculateMedian(ArrayUtils.toPrimitive(medianRedSignals
                .toArray(new Double[0])));
        logger.debug("Calculate baseline median for red signals: {}", bMedianRedSignal);

        double bMeanRedSignalSD = SignalProcessorHelper.calculateMeanSD(ArrayUtils.toPrimitive(meanRedSignals
                .toArray(new Double[0])));
        logger.debug("Calculate baseline mean standard deviation for red signals: {}", bMeanRedSignalSD);

        double bMedianRedSignalSD = SignalProcessorHelper.calculateMedianSD(ArrayUtils.toPrimitive(medianRedSignals
                .toArray(new Double[0])));
        logger.debug("Calculate baseline median standard deviation for red signals: {}", bMedianRedSignalSD);

        
        
        double bMeanLogRatio = SignalProcessorHelper
                .calculateMean(ArrayUtils.toPrimitive(meanLogRatios.toArray(new Double[0])));
        logger.debug("Calculate baseline mean for log ratios: {}", bMeanLogRatio);

        double bMedianLogRatio = SignalProcessorHelper.calculateMedian(ArrayUtils.toPrimitive(medianLogRatios
                .toArray(new Double[0])));
        logger.debug("Calculate baseline median for log ratios: {}", bMedianLogRatio);

        double bMeanLogRatioSD = SignalProcessorHelper.calculateMeanSD(ArrayUtils.toPrimitive(meanLogRatios
                .toArray(new Double[0])));
        logger.debug("Calculate baseline mean standard deviation for log ratios: {}", bMeanLogRatioSD);

        double bMedianLogRatioSD = SignalProcessorHelper.calculateMedianSD(ArrayUtils.toPrimitive(medianLogRatios
                .toArray(new Double[0])));
        logger.debug("Calculate baseline median standard deviation for log ratios: {}", bMedianLogRatioSD);
        
        

        baselineAverage.setBmeangreensignal(bMeanGreenSignal);
        baselineAverage.setBmeanredsignal(bMeanRedSignal);
        baselineAverage.setBmeanlogratio(bMeanLogRatio);

        baselineAverage.setBmeangreensignalsd(bMeanGreenSignalSD);
        baselineAverage.setBmeanredsignalsd(bMeanRedSignalSD);
        baselineAverage.setBmeanlogratiosd(bMeanLogRatioSD);

        baselineAverage.setBmediangreensignal(bMedianGreenSignal);
        baselineAverage.setBmedianredsignal(bMedianRedSignal);
        baselineAverage.setBmedianlogratio(bMedianLogRatio);

        baselineAverage.setBmediangreensignalsd(bMedianGreenSignalSD);
        baselineAverage.setBmedianredsignalsd(bMedianRedSignalSD);
        baselineAverage.setBmedianlogratiosd(bMedianLogRatioSD);

        return baselineAverage;
    }

    private void processZScores(final Configuration configuration) {
        logger.info("Calculating and saving ZScores in the database.");
        uiProgressObserver.publishProgressInfo("Calculating and saving ZScores in the database.");
        service.saveZScores(configuration);
    }

    // public void setProgressObserverDelegate(final ProgressObserverWrapper progressObserverWrapper) {
    // this.progressObserverWrapper = progressObserverWrapper;
    // }
    //
    // public ProgressObserverWrapper getProgressObserverDelegate() {
    // return this.progressObserverWrapper;
    // }

    public void setProgressObserver(final ProgressObserver uiProgressObserver) {
        this.uiProgressObserver = uiProgressObserver;
    }
}
