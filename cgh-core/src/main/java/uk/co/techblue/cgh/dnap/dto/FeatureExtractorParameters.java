package uk.co.techblue.cgh.dnap.dto;

/**
 * The Class FeatureExtractorParameters.
 * @author dheeraj
 */
public class FeatureExtractorParameters extends BaseDto{
    
    /** The feature extractor barcode. */
    private String featureExtractorBarcode;
    
    /** The scan date. */
    private String scanDate;

    /** The short array id. */
    private double shortArrayId;
    
    private String protocolName;

    private String Grid_GenomicBuild;

    private String FeatureExtractor_ScanFileName;

    private String FeatureExtractor_DesignFileName;

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
     * Gets the short array id.
     *
     * @return the short array id
     */
    public double getShortArrayId() {
        return shortArrayId;
    }

    /**
     * Sets the short array id.
     *
     * @param shortArrayId the new short array id
     */
    public void setShortArrayId(double shortArrayId) {
        this.shortArrayId = shortArrayId;
    }


    public String getProtocolName() {
        return protocolName;
    }

    public void setProtocolName(String protocolName) {
        this.protocolName = protocolName;
    }

    public String getGrid_GenomicBuild() {
        return Grid_GenomicBuild;
    }

    public void setGrid_GenomicBuild(String grid_GenomicBuild) {
        Grid_GenomicBuild = grid_GenomicBuild;
    }

    public String getFeatureExtractor_ScanFileName() {
        return FeatureExtractor_ScanFileName;
    }

    public void setFeatureExtractor_ScanFileName(String featureExtractor_ScanFileName) {
        FeatureExtractor_ScanFileName = featureExtractor_ScanFileName;
    }

    public String getFeatureExtractor_DesignFileName() {
        return FeatureExtractor_DesignFileName;
    }

    public void setFeatureExtractor_DesignFileName(String featureExtractor_DesignFileName) {
        FeatureExtractor_DesignFileName = featureExtractor_DesignFileName;
    }

}
