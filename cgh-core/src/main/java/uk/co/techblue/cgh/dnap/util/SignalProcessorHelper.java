package uk.co.techblue.cgh.dnap.util;

import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.SHORT_ARRAY_ID_CONST_1_1;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.SHORT_ARRAY_ID_CONST_1_2;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.SHORT_ARRAY_ID_CONST_1_3;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.SHORT_ARRAY_ID_CONST_1_4;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.SHORT_ARRAY_ID_CONST_2_1;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.SHORT_ARRAY_ID_CONST_2_2;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.SHORT_ARRAY_ID_CONST_2_3;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.SHORT_ARRAY_ID_CONST_2_4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.rank.Median;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

import uk.co.techblue.cgh.dnap.annotation.PersistProperties;
import uk.co.techblue.cgh.dnap.annotation.Property;
import uk.co.techblue.cgh.dnap.configuration.GeneralConfiguration;
import uk.co.techblue.cgh.dnap.configuration.IConfiguration;
import uk.co.techblue.cgh.dnap.dto.SignalFeatures;
import uk.co.techblue.cgh.dnap.exception.CGHProcessorException;
import uk.co.techblue.cgh.dnap.signalprocessor.CsvToBeanIterator;

// TODO: Auto-generated Javadoc
/**
 * The Class SignalProcessorHelper.
 * @author dheeraj
 */
public class SignalProcessorHelper {

    /**
     * Gets the fE short array id.
     * 
     * @param featureExtractorBarCode the feature extractor bar code
     * @return the fE short array id
     * @throws CGHProcessorException the nHS processor exception
     */
    public static double getFEShortArrayId(final String featureExtractorBarCode) throws CGHProcessorException {

        GeneralConfiguration generalProperties = SignalProcessorHelper.getConfigurationProperties(GeneralConfiguration.class);

        double shortArrayId = 0;
        String value = null;
        String suffix = StringUtils.substringAfter(featureExtractorBarCode, "_");
        String prefix = StringUtils.substringBefore(featureExtractorBarCode, "_");
        String part = StringUtils.substring(prefix, prefix.length() - 3, prefix.length());
        if (suffix.equals(SHORT_ARRAY_ID_CONST_1_1)) {
            value = generalProperties.getSystematicConst1();
        } else if (suffix.equals(SHORT_ARRAY_ID_CONST_1_2)) {
            value = generalProperties.getSystematicConst2();
        } else if (suffix.equals(SHORT_ARRAY_ID_CONST_1_3)) {
            value = generalProperties.getSystematicConst3();
        } else if (suffix.equals(SHORT_ARRAY_ID_CONST_1_4)) {
            value = generalProperties.getSystematicConst4();
        } else if (suffix.equals(SHORT_ARRAY_ID_CONST_2_1)) {
            value = generalProperties.getSystematicConst5();
        } else if (suffix.equals(SHORT_ARRAY_ID_CONST_2_2)) {
            value = generalProperties.getSystematicConst6();
        } else if (suffix.equals(SHORT_ARRAY_ID_CONST_2_3)) {
            value = generalProperties.getSystematicConst7();
        } else if (suffix.equals(SHORT_ARRAY_ID_CONST_2_4)) {
            value = generalProperties.getSystematicConst8();
        }
        shortArrayId = NumberUtils.toDouble(part + "." + value);
        return shortArrayId;
    }

    /**
     * Process systematic name.
     * 
     * @param feature the feature
     * @return the signal features
     */
    public static SignalFeatures processSystematicName(final SignalFeatures feature) {
        String systematicName = feature.getSystematicName();
        if (systematicName.startsWith("chr")) {
            String chromosome = StringUtils.substringBefore(systematicName, ":");
            long startPosition = NumberUtils.toLong(StringUtils.substringBetween(systematicName, ":", "-"));
            long stopPosition = NumberUtils.toLong(StringUtils.substringAfterLast(systematicName, "-"));
            feature.setChromosome(chromosome);
            feature.setStartPosition(startPosition);
            feature.setStopPosition(stopPosition);
        }
        return feature;
    }

    /**
     * Calculate mean.
     * 
     * @param values the values
     * @return the double
     */
    public static double calculateMean(final double[] values) {
        Mean mean = new Mean();
        return mean.evaluate(values);
    }

    /**
     * Calculate median.
     * 
     * @param values the values
     * @return the double
     */
    public static double calculateMedian(final double[] values) {
        Median median = new Median();
        return median.evaluate(values);
    }

    /**
     * Calculate mean sd.
     * 
     * @param values the values
     * @return the double
     */
    public static double calculateMeanSD(final double[] values) {
        StandardDeviation stdDeviation = new StandardDeviation();
        return stdDeviation.evaluate(values);
    }

    /**
     * Calculate median sd.
     * 
     * @param values the values
     * @return the double
     */
    public static double calculateMedianSD(final double[] values) {
        StandardDeviation stdDeviation = new StandardDeviation();
        return stdDeviation.evaluate(values, calculateMedian(values));
    }

    /**
     * Gets the properties from file.
     * 
     * @param filePath the file path
     * @return the properties from file
     * @throws IOException
     */
    @SuppressWarnings("resource")
    public static Properties getPropertiesFromFile(final String filePath) throws IOException {
        final Properties properties = new Properties();
        InputStream inputStream = null;
        if (filePath.startsWith(FileUtils.getUserDirectoryPath())) {
            inputStream = new FileInputStream(filePath);
        } else {
            inputStream = SignalProcessorHelper.class.getResourceAsStream(filePath);
        }
        try {
            properties.load(inputStream);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return properties;
    }

    /**
     * Gets the configuration properties.
     * 
     * @param <T> the generic type
     * @param typeClass the type class
     * @return the configuration properties
     * @throws CGHProcessorException the nHS processor exception
     */
    public static <T> T getConfigurationProperties(Class<T> typeClass) throws CGHProcessorException {
        Properties props = getProperties(typeClass);
        T beanInstance = null;
        if (props == null) {
            return beanInstance;
        }
        try {
            beanInstance = typeClass.newInstance();
        } catch (InstantiationException e) {
            throw new CGHProcessorException("An error occurred while reading the configuration properties " + typeClass, e);
        } catch (IllegalAccessException e) {
            throw new CGHProcessorException("An error occurred while reading the configuration properties " + typeClass, e);
        }
        for (final Field classField : typeClass.getDeclaredFields()) {
            if (classField.isAnnotationPresent(Property.class)) {
                final Property property = classField.getAnnotation(Property.class);
                final String propertyName = property.name();
                final String propertyValue = props.getProperty(propertyName);
                classField.setAccessible(true);
                try {
                    classField.set(beanInstance, propertyValue);
                } catch (IllegalArgumentException e) {
                    throw new CGHProcessorException(
                            "An error occurred while reading the configuration properties " + typeClass, e);
                } catch (IllegalAccessException e) {
                    throw new CGHProcessorException(
                            "An error occurred while reading the configuration properties " + typeClass, e);
                }
            }
        }
        return beanInstance;
    }

    /**
     * The main method.
     * 
     * @param args the arguments
     */
    public static void main(String[] args) {
    }

    /**
     * Store properties file.
     * 
     * @param properties the properties
     * @param configuration the configuration
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void storePropertiesFile(final Properties properties, IConfiguration configuration) throws IOException {
        if (!configuration.getClass().isAnnotationPresent(PersistProperties.class)) {
            throw new IllegalStateException("The class must be annotated with " + PersistProperties.class.getName()
                    + " annotation.");
        }
        PersistProperties persistProperties = configuration.getClass().getAnnotation(PersistProperties.class);
        String propertiesFilePath = FilenameUtils.separatorsToSystem(System
                .getProperty(persistProperties.basePath().getValue()) + persistProperties.path());

        File file = new File(propertiesFilePath);
        if (!file.exists()) {
            FileUtils.touch(file);
            propertiesFilePath = file.getAbsolutePath();
        }

        FileOutputStream fos = new FileOutputStream(propertiesFilePath);
        properties.store(fos, null);
    }

    /**
     * Gets the properties.
     * 
     * @param <T> the generic type
     * @param typeClass the type class
     * @return the properties
     * @throws CGHProcessorException the nHS processor exception
     */
    public static <T> Properties getProperties(Class<T> typeClass) throws CGHProcessorException {
        if (!typeClass.isAnnotationPresent(PersistProperties.class)) {
            throw new IllegalStateException("The class must be annotated with " + PersistProperties.class.getName()
                    + " annotation.");
        }
        PersistProperties persistProperties = typeClass.getAnnotation(PersistProperties.class);
        String propertiesFilePath = System.getProperty(persistProperties.basePath().getValue()) + persistProperties.path();

        if (!new File(propertiesFilePath).exists()) {
            propertiesFilePath = persistProperties.defaultPath();
        }

        Properties props = null;
        if (StringUtils.isBlank(propertiesFilePath)) {
            return props;
        }

        try {
            props = getPropertiesFromFile(propertiesFilePath);
        } catch (FileNotFoundException nfne) {
            throw new CGHProcessorException("An error occurred while reading the general configuration file, "
                    + propertiesFilePath, nfne);
        } catch (IOException ioe) {
            throw new CGHProcessorException("An error occurred while reading the general configuration file, "
                    + propertiesFilePath, ioe);
        }
        return props;
    }

    /**
     * Gets the custom csv to bean iterator.
     * 
     * @param <T> the generic type
     * @param type the type
     * @param map the map
     * @param reader the reader
     * @return the custom csv to bean iterator
     * @throws CGHProcessorException
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static <T> CsvToBeanIterator<T> getCustomCsvToBeanIterator(Class<T> type, Map<String, String> map, CSVReader reader)
            throws CGHProcessorException {
        HeaderColumnNameTranslateMappingStrategy<T> mappingStrategy = new HeaderColumnNameTranslateMappingStrategy<T>();
        mappingStrategy.setType(type);
        mappingStrategy.setColumnMapping(map);
        try {
            mappingStrategy.captureHeader(reader);
        } catch (IOException ioe) {
            throw new CGHProcessorException("An error occurred while mapping the csv columns to bean properties", ioe);
        }
        return new CsvToBeanIterator<T>(mappingStrategy, reader);
    }

    public static String getSystemTemporaryPath() {
        String tmpPath = System.getProperty("java.io.tmpdir");
        if (tmpPath != null) {
            if (tmpPath.endsWith(File.separator)) {
                return tmpPath;
            } else {
                return (tmpPath += File.separator);
            }
        }
        return tmpPath;
    }
}
