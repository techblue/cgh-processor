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
}
