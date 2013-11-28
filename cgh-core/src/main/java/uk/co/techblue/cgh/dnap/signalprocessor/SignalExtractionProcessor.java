package uk.co.techblue.cgh.dnap.signalprocessor;

import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.ARRAY_ID;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.FEATURE_EXTRACTOR_BARCODE;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.GLOBAL_DISPLAY_NAME;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.GREEN_SAMPLE;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.G_BG_NON_UNIF_OL;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.G_FEAT_NON_UNIF_OL;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.G_PROCESSED_SIGNAL;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.G_SATURATED;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.LOG_RATIO;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.METRIC_ANY_COLOR_PRCNT_FEAT_NON_UNIF_OL;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.METRIC_DERIVATIVE_LR_SPREAD;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.METRIC_G_SIGNAL2NOISE;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.METRIC_G_SIGNAL_INTENSITY;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.METRIC_R_SIGNAL2NOISE;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.METRIC_R_SIGNAL_INTENSITY;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.POLARITY;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.PROBE_NAME;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.RED_SAMPLE;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.R_BG_NON_UNIF_OL;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.R_FEAT_NON_UNIF_OL;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.R_PROCESSED_SIGNAL;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.R_SATURATED;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.SCAN_DATE;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.SYSTEMATIC_NAME;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.techblue.cgh.dnap.configuration.CsvConfiguration;
import uk.co.techblue.cgh.dnap.configuration.DefaultConfiguration;
import uk.co.techblue.cgh.dnap.dto.Attribute;
import uk.co.techblue.cgh.dnap.dto.FeatureExtractorParameters;
import uk.co.techblue.cgh.dnap.dto.FeatureExtractorStatistics;
import uk.co.techblue.cgh.dnap.dto.Region;
import uk.co.techblue.cgh.dnap.dto.SignalFeatures;
import uk.co.techblue.cgh.dnap.exception.CsvToBeanException;
import uk.co.techblue.cgh.dnap.exception.CGHProcessorException;
import uk.co.techblue.cgh.dnap.util.SignalProcessorHelper;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

/**
 * The Class SignalExtractionProcessor.
 * 
 * @author dheeraj
 */
public class SignalExtractionProcessor {

    /** The Constant LOGGER. */
    private final static Logger LOGGER = LoggerFactory.getLogger(SignalExtractionProcessor.class);

    /** The cgh properties. */
    private static DefaultConfiguration defaultConfiguration = getDefaultConfiguration();

    /** The csv mapping properties. */
    private static CsvConfiguration csvMappingConfiguration = getCsvConfiguration();

    protected static DefaultConfiguration getDefaultConfiguration() {
        try {
            return SignalProcessorHelper.getConfigurationProperties(DefaultConfiguration.class);
        } catch (CGHProcessorException cghe) {
            throw new RuntimeException("An error occurred while loading application default configuration properties.", cghe);
        }
    }

    protected static CsvConfiguration getCsvConfiguration() {
        try {
            return SignalProcessorHelper.getConfigurationProperties(CsvConfiguration.class);
        } catch (CGHProcessorException cghe) {
            throw new RuntimeException("An error occurred while loading application CSV configuration properties.", cghe);
        }
    }

    /**
     * Gets the attributes.
     * 
     * @return the attributes
     * @throws CGHProcessorException
     */
    public static List<Attribute> getAttributes(final String filePath) throws CGHProcessorException {

        List<Attribute> attributes = new ArrayList<Attribute>();
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(filePath), '\t', '\'');
        } catch (FileNotFoundException fnfe) {
            throw new CGHProcessorException("An error occurred while reading attribs.txt from the location " + filePath, fnfe);
        }
        Map<String, String> map = new Hashtable<String, String>();
        map.put(csvMappingConfiguration.getArrayId(), ARRAY_ID);
        map.put(csvMappingConfiguration.getGlobalDisplayName(), GLOBAL_DISPLAY_NAME);
        map.put(csvMappingConfiguration.getRedSample(), RED_SAMPLE);
        map.put(csvMappingConfiguration.getGreenSample(), GREEN_SAMPLE);
        map.put(csvMappingConfiguration.getPolarity(), POLARITY);

        try {
            CsvToBeanIterator<Attribute> csvIterator = null;
            try {
                csvIterator = SignalProcessorHelper.getCustomCsvToBeanIterator(Attribute.class, map, reader);
            } catch (CGHProcessorException cghe) {
                throw cghe;
            }
            Attribute attribute = null;
            while (null != (attribute = csvIterator.readNext())) {
                attributes.add(attribute);
            }
        } catch (CsvToBeanException ctbe) {
            throw new CGHProcessorException("Error occurred while reading the source tab delimited text file.", ctbe);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ioe) {
                    LOGGER.warn("Error occurred while closing the reader.", ioe);
                }
            }
        }
        return attributes;

    }

    public static List<Region> getRegions(final String filePath) throws CGHProcessorException {

        List<Region> regions = new ArrayList<Region>();
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(filePath), '\t', '\'');
        } catch (FileNotFoundException fnfe) {
            throw new CGHProcessorException("An error occurred while reading regions.txt from the location " + filePath, fnfe);
        }

        try {
            ColumnPositionMappingStrategy<Region> columnPostitionMappingStrategy = new ColumnPositionMappingStrategy<Region>();
            columnPostitionMappingStrategy.setType(Region.class);
            String[] columns = new String[] { "chromosome", "startPosition", "stopPosition" };
            columnPostitionMappingStrategy.setColumnMapping(columns);

            CsvToBean<Region> csv = new CsvToBean<Region>();
            regions = csv.parse(columnPostitionMappingStrategy, reader);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ioe) {
                    LOGGER.warn("Error occurred while closing the reader.", ioe);
                }
            }
        }
        return regions;
    }

    /**
     * Gets the fE params.
     * 
     * @return the fE params
     * @throws CGHProcessorException
     */
    public static FeatureExtractorParameters getFEParams(final String filePath) throws CGHProcessorException {
        CSVReader reader = null;
        FeatureExtractorParameters feParamHeader = null;
        try {
            reader = new CSVReader(new FileReader(filePath), '\t', '\'', NumberUtils.toInt(defaultConfiguration
                    .getFeParamsStartOffset()));
        } catch (FileNotFoundException fnfe) {
            throw new CGHProcessorException("An error occurred while reading data file from the location " + filePath, fnfe);
        }
        int counter = 0;
        Map<String, String> map = new Hashtable<String, String>();
        map.put(csvMappingConfiguration.getFeatureExtractorBarcode(), FEATURE_EXTRACTOR_BARCODE);
        map.put(csvMappingConfiguration.getScanDate(), SCAN_DATE);
        try {
            CsvToBeanIterator<FeatureExtractorParameters> csvIterator = null;
            try {
                csvIterator = SignalProcessorHelper.getCustomCsvToBeanIterator(FeatureExtractorParameters.class, map, reader);
            } catch (CGHProcessorException cghe) {
                throw cghe;
            }
            while (null != (feParamHeader = csvIterator.readNext())) {
                counter++;
                if (counter == NumberUtils.toInt(defaultConfiguration.getLoopBreakIndex())) {
                    break;
                }
            }
        } catch (CsvToBeanException ctbe) {
            throw new CGHProcessorException("Error occurred while reading the source tab delimited text file.", ctbe);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ioe) {
                    LOGGER.warn("Error occurred while closing the reader.", ioe);
                }
            }
        }
        return feParamHeader;
    }

    /**
     * Gets the fE stats.
     * 
     * @return the fE stats
     * @throws CGHProcessorException
     */
    public static FeatureExtractorStatistics getFEStats(final String filePath) throws CGHProcessorException {
        CSVReader reader = null;
        FeatureExtractorStatistics statHeader = null;
        try {
            reader = new CSVReader(new FileReader(filePath), '\t', '\'', NumberUtils.toInt(defaultConfiguration
                    .getStatsStartOffset()));
        } catch (FileNotFoundException fnfe) {
            throw new CGHProcessorException("An error occurred while reading data file from the location " + filePath, fnfe);
        }
        int counter = 0;
        Map<String, String> map = new Hashtable<String, String>();
        map.put(csvMappingConfiguration.getMetricAnyColorPrcntFeatNonUnifOL(), METRIC_ANY_COLOR_PRCNT_FEAT_NON_UNIF_OL);
        map.put(csvMappingConfiguration.getMetricDerivativeLRSpread(), METRIC_DERIVATIVE_LR_SPREAD);
        map.put(csvMappingConfiguration.getMetricGSignal2Noise(), METRIC_G_SIGNAL2NOISE);
        map.put(csvMappingConfiguration.getMetricGSignalIntensity(), METRIC_G_SIGNAL_INTENSITY);
        map.put(csvMappingConfiguration.getMetricRSignal2Noise(), METRIC_R_SIGNAL2NOISE);
        map.put(csvMappingConfiguration.getMetricRSignalIntensity(), METRIC_R_SIGNAL_INTENSITY);
        try {
            CsvToBeanIterator<FeatureExtractorStatistics> csvIterator = null;
            try {
                csvIterator = SignalProcessorHelper.getCustomCsvToBeanIterator(FeatureExtractorStatistics.class, map, reader);
            } catch (CGHProcessorException cghe) {
                throw cghe;
            }
            while (null != (statHeader = csvIterator.readNext())) {
                counter++;
                if (counter == NumberUtils.toInt(defaultConfiguration.getLoopBreakIndex())) {
                    break;
                }
            }
        } catch (CsvToBeanException ctbe) {
            throw new CGHProcessorException("Error occurred while reading the source tab delimited text file.", ctbe);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ioe) {
                    LOGGER.warn("Error occurred while closing the reader.", ioe);
                }
            }
        }
        return statHeader;
    }

    /**
     * Gets the fE features.
     * 
     * @return the fE features
     * @throws CGHProcessorException
     */
    public static List<SignalFeatures> getFEFeatures(final String filePath) throws CGHProcessorException {
        List<SignalFeatures> featuresData = new ArrayList<SignalFeatures>();
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(filePath), '\t', '\'', NumberUtils.toInt(defaultConfiguration
                    .getFeaturesStartOffset()));
        } catch (FileNotFoundException fnfe) {
            throw new CGHProcessorException("An error occurred while reading data file from the location " + filePath, fnfe);
        }
        Map<String, String> map = new Hashtable<String, String>();
        map.put(csvMappingConfiguration.getProbeName(), PROBE_NAME);
        map.put(csvMappingConfiguration.getSystematicName(), SYSTEMATIC_NAME);
        map.put(csvMappingConfiguration.getLogRatio(), LOG_RATIO);
        map.put(csvMappingConfiguration.getgProcessedSignal(), G_PROCESSED_SIGNAL);
        map.put(csvMappingConfiguration.getrProcessedSignal(), R_PROCESSED_SIGNAL);
        map.put(csvMappingConfiguration.getIsGSaturated(), G_SATURATED);
        map.put(csvMappingConfiguration.getIsRSaturated(), R_SATURATED);
        map.put(csvMappingConfiguration.getIsGFeatNonUnifOL(), G_FEAT_NON_UNIF_OL);
        map.put(csvMappingConfiguration.getIsRFeatNonUnifOL(), R_FEAT_NON_UNIF_OL);
        map.put(csvMappingConfiguration.getIsGBGNonUnifOL(), G_BG_NON_UNIF_OL);
        map.put(csvMappingConfiguration.getIsRBGNonUnifOL(), R_BG_NON_UNIF_OL);

        try {
            CsvToBeanIterator<SignalFeatures> csvIterator = null;
            try {
                csvIterator = SignalProcessorHelper.getCustomCsvToBeanIterator(SignalFeatures.class, map, reader);
            } catch (CGHProcessorException cghe) {
                throw cghe;
            }
            SignalFeatures signalFeature = null;
            while (null != (signalFeature = csvIterator.readNext())) {
                featuresData.add(signalFeature);
            }
        } catch (CsvToBeanException ctbe) {
            throw new CGHProcessorException("Error occurred while reading the source tab delimited text file.", ctbe);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ioe) {
                    LOGGER.warn("Error occurred while closing the reader.", ioe);
                }
            }
        }
        return featuresData;
    }

}
