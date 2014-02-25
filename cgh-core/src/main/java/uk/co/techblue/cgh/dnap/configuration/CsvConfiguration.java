package uk.co.techblue.cgh.dnap.configuration;

import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.CSV_MAPPING_PROPERTIES;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.PROPERTIES_PATH;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.USER_PROPERTIES_PATH;
import uk.co.techblue.cgh.dnap.annotation.PersistProperties;
import uk.co.techblue.cgh.dnap.annotation.Property;
import uk.co.techblue.cgh.dnap.annotation.PersistProperties.ResourceBasePath;
import uk.co.techblue.cgh.dnap.annotation.Property.Type;

/**
 * The Class CsvConfiguration.
 * @author dheeraj
 */
@PersistProperties(defaultPath = PROPERTIES_PATH + CSV_MAPPING_PROPERTIES, basePath = ResourceBasePath.USER_HOME, path = USER_PROPERTIES_PATH
        + CSV_MAPPING_PROPERTIES)
public class CsvConfiguration implements IConfiguration{

    /** The feature extractor barcode. */
    @Property(name = "featureExtractorBarcode", displayName = "Feature Extractor Barcode")
    private String featureExtractorBarcode;

    /** The scan date. */
    @Property(name = "scanDate", displayName = "Scan Date")
    private String scanDate;

    /** The metric any color prcnt feat non unif ol. */
    @Property(name = "metricAnyColorPrcntFeatNonUnifOL", displayName = "Metric Any Color Percent Feature Unif OL")
    private String metricAnyColorPrcntFeatNonUnifOL;

    /** The metric derivative lr spread. */
    @Property(name = "metricDerivativeLRSpread", displayName = "Metric Derivative LR Spread")
    private String metricDerivativeLRSpread;

    /** The metric g signal intensity. */
    @Property(name = "metricGSignalIntensity", displayName = "Metric Green Signal Intensity")
    private String metricGSignalIntensity;

    /** The metric g signal2 noise. */
    @Property(name = "metricGSignal2Noise", displayName = "Metric Green Signal 2 Noise")
    private String metricGSignal2Noise;

    /** The metric r signal intensity. */
    @Property(name = "metricRSignalIntensity", displayName = "Metric Red Signal Intensity")
    private String metricRSignalIntensity;

    /** The metric r signal2 noise. */
    @Property(name = "metricRSignal2Noise", displayName = "Metric Red Signal 2 Noise")
    private String metricRSignal2Noise;

    /** The probe name. */
    @Property(name = "probeName", displayName = "Probe Name")
    private String probeName;

    /** The systematic name. */
    @Property(name = "systematicName", displayName = "Systematic Name")
    private String systematicName;
    
    /** The log ratio. */
    @Property(name = "logRatio", displayName = "Log Ratio")
    private String logRatio;

    /** The g processed signal. */
    @Property(name = "gProcessedSignal", displayName = "Green Processed Signal")
    private String gProcessedSignal;

    /** The r processed signal. */
    @Property(name = "rProcessedSignal", displayName = "Red Processed Signal")
    private String rProcessedSignal;

    /** The is g saturated. */
    @Property(name = "isGSaturated", type = Type.BOOLEAN, displayName = "Is Green Signal Saturated")
    private String isGSaturated;

    /** The is r saturated. */
    @Property(name = "isRSaturated", type = Type.BOOLEAN, displayName = "Is Red Signal Saturated")
    private String isRSaturated;

    /** The is g feat non unif ol. */
    @Property(name = "isGFeatNonUnifOL", type = Type.BOOLEAN, displayName = "Is Green Signal Feature Non Unif OL")
    private String isGFeatNonUnifOL;

    /** The is r feat non unif ol. */
    @Property(name = "isRFeatNonUnifOL", type = Type.BOOLEAN, displayName = "Is Red Signal Feature Non Unif OL")
    private String isRFeatNonUnifOL;

    /** The is gbg non unif ol. */
    @Property(name = "isGBGNonUnifOL", type = Type.BOOLEAN, displayName = "Is Green Signal BG Non Unif OL")
    private String isGBGNonUnifOL;

    /** The is rbg non unif ol. */
    @Property(name = "isRBGNonUnifOL", type = Type.BOOLEAN, displayName = "Is Red Signal BG Non Unif OL")
    private String isRBGNonUnifOL;

    /** The array id. */
    @Property(name = "arrayId", displayName = "Array Id")
    private String arrayId;

    /** The global display name. */
    @Property(name = "globalDisplayName", displayName = "Global Display Name")
    private String globalDisplayName;

    /** The red sample. */
    @Property(name = "redSample", displayName = "Red Sample")
    private String redSample;

    /** The green sample. */
    @Property(name = "greenSample", displayName = "Green Sample")
    private String greenSample;

    /** The polarity. */
    @Property(name = "polarity", displayName = "Polarity")
    private String polarity;

    @Property(name = "protocolName", displayName = "protocolName")
    private String protocolName;

    @Property(name = "gridGenomicBuild", displayName = "gridGenomicBuild")
    private String gridGenomicBuild;

    @Property(name = "featureExtractorScanFilename", displayName = "featureExtractorScanFilename")
    private String featureExtractorScanFilename;

    @Property(name = "featureExtractorDesignFilename", displayName = "featureExtractorDesignFilename")
    private String featureExtractorDesignFilename;

    @Property(name = "gIsWellAboveBG", type = Type.BOOLEAN, displayName = "gIsWellAboveBG")
    private String gIsWellAboveBG;

    @Property(name = "rIsWellAboveBG", type = Type.BOOLEAN, displayName = "rIsWellAboveBG")
    private String rIsWellAboveBG;

    @Property(name = "sex", displayName = "Sex")
    private String sex;

    /**
     * Gets the feature extractor barcode.
     *
     * @return the feature extractor barcode
     */
    public String getFeatureExtractorBarcode() {
        return featureExtractorBarcode;
    }

    /**
     * Sets the feature extractor barcode.
     *
     * @param featureExtractorBarcode the new feature extractor barcode
     */
    public void setFeatureExtractorBarcode(String featureExtractorBarcode) {
        this.featureExtractorBarcode = featureExtractorBarcode;
    }

    /**
     * Gets the scan date.
     *
     * @return the scan date
     */
    public String getScanDate() {
        return scanDate;
    }

    /**
     * Sets the scan date.
     *
     * @param scanDate the new scan date
     */
    public void setScanDate(String scanDate) {
        this.scanDate = scanDate;
    }

    /**
     * Gets the metric any color prcnt feat non unif ol.
     *
     * @return the metric any color prcnt feat non unif ol
     */
    public String getMetricAnyColorPrcntFeatNonUnifOL() {
        return metricAnyColorPrcntFeatNonUnifOL;
    }

    /**
     * Sets the metric any color prcnt feat non unif ol.
     *
     * @param metricAnyColorPrcntFeatNonUnifOL the new metric any color prcnt feat non unif ol
     */
    public void setMetricAnyColorPrcntFeatNonUnifOL(String metricAnyColorPrcntFeatNonUnifOL) {
        this.metricAnyColorPrcntFeatNonUnifOL = metricAnyColorPrcntFeatNonUnifOL;
    }

    /**
     * Gets the metric derivative lr spread.
     *
     * @return the metric derivative lr spread
     */
    public String getMetricDerivativeLRSpread() {
        return metricDerivativeLRSpread;
    }

    /**
     * Sets the metric derivative lr spread.
     *
     * @param metricDerivativeLRSpread the new metric derivative lr spread
     */
    public void setMetricDerivativeLRSpread(String metricDerivativeLRSpread) {
        this.metricDerivativeLRSpread = metricDerivativeLRSpread;
    }

    /**
     * Gets the metric g signal intensity.
     *
     * @return the metric g signal intensity
     */
    public String getMetricGSignalIntensity() {
        return metricGSignalIntensity;
    }

    /**
     * Sets the metric g signal intensity.
     *
     * @param metricGSignalIntensity the new metric g signal intensity
     */
    public void setMetricGSignalIntensity(String metricGSignalIntensity) {
        this.metricGSignalIntensity = metricGSignalIntensity;
    }

    /**
     * Gets the metric g signal2 noise.
     *
     * @return the metric g signal2 noise
     */
    public String getMetricGSignal2Noise() {
        return metricGSignal2Noise;
    }

    /**
     * Sets the metric g signal2 noise.
     *
     * @param metricGSignal2Noise the new metric g signal2 noise
     */
    public void setMetricGSignal2Noise(String metricGSignal2Noise) {
        this.metricGSignal2Noise = metricGSignal2Noise;
    }

    /**
     * Gets the metric r signal intensity.
     *
     * @return the metric r signal intensity
     */
    public String getMetricRSignalIntensity() {
        return metricRSignalIntensity;
    }

    /**
     * Sets the metric r signal intensity.
     *
     * @param metricRSignalIntensity the new metric r signal intensity
     */
    public void setMetricRSignalIntensity(String metricRSignalIntensity) {
        this.metricRSignalIntensity = metricRSignalIntensity;
    }

    /**
     * Gets the metric r signal2 noise.
     *
     * @return the metric r signal2 noise
     */
    public String getMetricRSignal2Noise() {
        return metricRSignal2Noise;
    }

    /**
     * Sets the metric r signal2 noise.
     *
     * @param metricRSignal2Noise the new metric r signal2 noise
     */
    public void setMetricRSignal2Noise(String metricRSignal2Noise) {
        this.metricRSignal2Noise = metricRSignal2Noise;
    }

    /**
     * Gets the probe name.
     *
     * @return the probe name
     */
    public String getProbeName() {
        return probeName;
    }

    /**
     * Sets the probe name.
     *
     * @param probeName the new probe name
     */
    public void setProbeName(String probeName) {
        this.probeName = probeName;
    }

    /**
     * Gets the systematic name.
     *
     * @return the systematic name
     */
    public String getSystematicName() {
        return systematicName;
    }

    /**
     * Sets the systematic name.
     *
     * @param systematicName the new systematic name
     */
    public void setSystematicName(String systematicName) {
        this.systematicName = systematicName;
    }
    
    /**
     * Gets the log ratio.
     *
     * @return the log ratio
     */
    public String getLogRatio() {
        return logRatio;
    }

    /**
     * Sets the log ratio.
     *
     * @param logRatio the new log ratio
     */
    public void setLogRatio(String logRatio) {
        this.logRatio = logRatio;
    }

    /**
     * Gets the g processed signal.
     *
     * @return the g processed signal
     */
    public String getgProcessedSignal() {
        return gProcessedSignal;
    }

    /**
     * Sets the g processed signal.
     *
     * @param gProcessedSignal the new g processed signal
     */
    public void setgProcessedSignal(String gProcessedSignal) {
        this.gProcessedSignal = gProcessedSignal;
    }

    /**
     * Gets the r processed signal.
     *
     * @return the r processed signal
     */
    public String getrProcessedSignal() {
        return rProcessedSignal;
    }

    /**
     * Sets the r processed signal.
     *
     * @param rProcessedSignal the new r processed signal
     */
    public void setrProcessedSignal(String rProcessedSignal) {
        this.rProcessedSignal = rProcessedSignal;
    }

    /**
     * Gets the checks if is g saturated.
     *
     * @return the checks if is g saturated
     */
    public String getIsGSaturated() {
        return isGSaturated;
    }

    /**
     * Sets the checks if is g saturated.
     *
     * @param isGSaturated the new checks if is g saturated
     */
    public void setIsGSaturated(String isGSaturated) {
        this.isGSaturated = isGSaturated;
    }

    /**
     * Gets the checks if is r saturated.
     *
     * @return the checks if is r saturated
     */
    public String getIsRSaturated() {
        return isRSaturated;
    }

    /**
     * Sets the checks if is r saturated.
     *
     * @param isRSaturated the new checks if is r saturated
     */
    public void setIsRSaturated(String isRSaturated) {
        this.isRSaturated = isRSaturated;
    }

    /**
     * Gets the checks if is g feat non unif ol.
     *
     * @return the checks if is g feat non unif ol
     */
    public String getIsGFeatNonUnifOL() {
        return isGFeatNonUnifOL;
    }

    /**
     * Sets the checks if is g feat non unif ol.
     *
     * @param isGFeatNonUnifOL the new checks if is g feat non unif ol
     */
    public void setIsGFeatNonUnifOL(String isGFeatNonUnifOL) {
        this.isGFeatNonUnifOL = isGFeatNonUnifOL;
    }

    /**
     * Gets the checks if is r feat non unif ol.
     *
     * @return the checks if is r feat non unif ol
     */
    public String getIsRFeatNonUnifOL() {
        return isRFeatNonUnifOL;
    }

    /**
     * Sets the checks if is r feat non unif ol.
     *
     * @param isRFeatNonUnifOL the new checks if is r feat non unif ol
     */
    public void setIsRFeatNonUnifOL(String isRFeatNonUnifOL) {
        this.isRFeatNonUnifOL = isRFeatNonUnifOL;
    }

    /**
     * Gets the checks if is gbg non unif ol.
     *
     * @return the checks if is gbg non unif ol
     */
    public String getIsGBGNonUnifOL() {
        return isGBGNonUnifOL;
    }

    /**
     * Sets the checks if is gbg non unif ol.
     *
     * @param isGBGNonUnifOL the new checks if is gbg non unif ol
     */
    public void setIsGBGNonUnifOL(String isGBGNonUnifOL) {
        this.isGBGNonUnifOL = isGBGNonUnifOL;
    }

    /**
     * Gets the checks if is rbg non unif ol.
     *
     * @return the checks if is rbg non unif ol
     */
    public String getIsRBGNonUnifOL() {
        return isRBGNonUnifOL;
    }

    /**
     * Sets the checks if is rbg non unif ol.
     *
     * @param isRBGNonUnifOL the new checks if is rbg non unif ol
     */
    public void setIsRBGNonUnifOL(String isRBGNonUnifOL) {
        this.isRBGNonUnifOL = isRBGNonUnifOL;
    }

    /**
     * Gets the array id.
     *
     * @return the array id
     */
    public String getArrayId() {
        return arrayId;
    }

    /**
     * Sets the array id.
     *
     * @param arrayId the new array id
     */
    public void setArrayId(String arrayId) {
        this.arrayId = arrayId;
    }

    /**
     * Gets the global display name.
     *
     * @return the global display name
     */
    public String getGlobalDisplayName() {
        return globalDisplayName;
    }

    /**
     * Sets the global display name.
     *
     * @param globalDisplayName the new global display name
     */
    public void setGlobalDisplayName(String globalDisplayName) {
        this.globalDisplayName = globalDisplayName;
    }

    /**
     * Gets the red sample.
     *
     * @return the red sample
     */
    public String getRedSample() {
        return redSample;
    }

    /**
     * Sets the red sample.
     *
     * @param redSample the new red sample
     */
    public void setRedSample(String redSample) {
        this.redSample = redSample;
    }

    /**
     * Gets the green sample.
     *
     * @return the green sample
     */
    public String getGreenSample() {
        return greenSample;
    }

    /**
     * Sets the green sample.
     *
     * @param greenSample the new green sample
     */
    public void setGreenSample(String greenSample) {
        this.greenSample = greenSample;
    }

    /**
     * Gets the polarity.
     *
     * @return the polarity
     */
    public String getPolarity() {
        return polarity;
    }

    /**
     * Sets the polarity.
     *
     * @param polarity the new polarity
     */
    public void setPolarity(String polarity) {
        this.polarity = polarity;
    }

    public String getProtocolName() {
        return protocolName;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    public String getGridGenomicBuild() {
        return gridGenomicBuild;
    }

    public void setGridGenomicBuild(String gridGenomicBuild) {
        this.gridGenomicBuild = gridGenomicBuild;
    }

    public String getFeatureExtractorScanFilename() {
        return featureExtractorScanFilename;
    }

    public void setFeatureExtractorScanFilename(String featureExtractorScanFilename) {
        this.featureExtractorScanFilename = featureExtractorScanFilename;
    }

    public String getFeatureExtractorDesignFilename() {
        return featureExtractorDesignFilename;
    }

    public void setFeatureExtractorDesignFilename(String featureExtractorDesignFilename) {
        this.featureExtractorDesignFilename = featureExtractorDesignFilename;
    }

    public String getgIsWellAboveBG() {
        return gIsWellAboveBG;
    }

    public void setgIsWellAboveBG(String gIsWellAboveBG) {
        this.gIsWellAboveBG = gIsWellAboveBG;
    }

    public String getrIsWellAboveBG() {
        return rIsWellAboveBG;
    }

    public void setrIsWellAboveBG(String rIsWellAboveBG) {
        this.rIsWellAboveBG = rIsWellAboveBG;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

}
