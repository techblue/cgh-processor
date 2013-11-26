package uk.co.techblue.cgh.dnap.dto;

/**
 * The Class SignalFeatures.
 * @author dheeraj
 */
public class SignalFeatures extends BaseDto{
    
    /** The probe name. */
    private String probeName;
    
    /** The systematic name. */
    private String systematicName;
    
    /** The log ratio. */
    private double logRatio;
    
    /** The g processed signal. */
    private double gProcessedSignal;
    
    /** The r processed signal. */
    private double rProcessedSignal;

    /** The is g saturated. */
    private boolean isGSaturated;
    
    /** The is r saturated. */
    private boolean isRSaturated;
    
    /** The is g feat non unif ol. */
    private boolean isGFeatNonUnifOL;
    
    /** The is r feat non unif ol. */
    private boolean isRFeatNonUnifOL;
    
    /** The is gbg non unif ol. */
    private boolean isGBGNonUnifOL;
    
    /** The is rbg non unif ol. */
    private boolean isRBGNonUnifOL;
    
    /** The chromosome. */
    private String chromosome;
    
    /** The start position. */
    private long startPosition;
    
    /** The stop position. */
    private long stopPosition;

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
    public double getLogRatio() {
        return logRatio;
    }

    /**
     * Sets the log ratio.
     *
     * @param logRatio the new log ratio
     */
    public void setLogRatio(double logRatio) {
        this.logRatio = logRatio;
    }

    /**
     * Gets the g processed signal.
     *
     * @return the g processed signal
     */
    public double getgProcessedSignal() {
        return gProcessedSignal;
    }

    /**
     * Sets the g processed signal.
     *
     * @param gProcessedSignal the new g processed signal
     */
    public void setgProcessedSignal(double gProcessedSignal) {
        this.gProcessedSignal = gProcessedSignal;
    }

    /**
     * Gets the r processed signal.
     *
     * @return the r processed signal
     */
    public double getrProcessedSignal() {
        return rProcessedSignal;
    }

    /**
     * Sets the r processed signal.
     *
     * @param rProcessedSignal the new r processed signal
     */
    public void setrProcessedSignal(double rProcessedSignal) {
        this.rProcessedSignal = rProcessedSignal;
    }

    /**
     * Checks if is g saturated.
     *
     * @return true, if is g saturated
     */
    public boolean isGSaturated() {
        return isGSaturated;
    }

    /**
     * Sets the g saturated.
     *
     * @param isGSaturated the new g saturated
     */
    public void setGSaturated(boolean isGSaturated) {
        this.isGSaturated = isGSaturated;
    }

    /**
     * Checks if is r saturated.
     *
     * @return true, if is r saturated
     */
    public boolean isRSaturated() {
        return isRSaturated;
    }

    /**
     * Sets the r saturated.
     *
     * @param isRSaturated the new r saturated
     */
    public void setRSaturated(boolean isRSaturated) {
        this.isRSaturated = isRSaturated;
    }

    /**
     * Checks if is g feat non unif ol.
     *
     * @return true, if is g feat non unif ol
     */
    public boolean isGFeatNonUnifOL() {
        return isGFeatNonUnifOL;
    }

    /**
     * Sets the g feat non unif ol.
     *
     * @param isGFeatNonUnifOL the new g feat non unif ol
     */
    public void setGFeatNonUnifOL(boolean isGFeatNonUnifOL) {
        this.isGFeatNonUnifOL = isGFeatNonUnifOL;
    }

    /**
     * Checks if is r feat non unif ol.
     *
     * @return true, if is r feat non unif ol
     */
    public boolean isRFeatNonUnifOL() {
        return isRFeatNonUnifOL;
    }

    /**
     * Sets the r feat non unif ol.
     *
     * @param isRFeatNonUnifOL the new r feat non unif ol
     */
    public void setRFeatNonUnifOL(boolean isRFeatNonUnifOL) {
        this.isRFeatNonUnifOL = isRFeatNonUnifOL;
    }

    /**
     * Checks if is gBG non unif ol.
     *
     * @return true, if is gBG non unif ol
     */
    public boolean isGBGNonUnifOL() {
        return isGBGNonUnifOL;
    }

    /**
     * Sets the gBG non unif ol.
     *
     * @param isGBGNonUnifOL the new gBG non unif ol
     */
    public void setGBGNonUnifOL(boolean isGBGNonUnifOL) {
        this.isGBGNonUnifOL = isGBGNonUnifOL;
    }

    /**
     * Checks if is rBG non unif ol.
     *
     * @return true, if is rBG non unif ol
     */
    public boolean isRBGNonUnifOL() {
        return isRBGNonUnifOL;
    }

    /**
     * Sets the rBG non unif ol.
     *
     * @param isRBGNonUnifOL the new rBG non unif ol
     */
    public void setRBGNonUnifOL(boolean isRBGNonUnifOL) {
        this.isRBGNonUnifOL = isRBGNonUnifOL;
    }

    /**
     * Gets the chromosome.
     *
     * @return the chromosome
     */
    public String getChromosome() {
        return chromosome;
    }

    /**
     * Sets the chromosome.
     *
     * @param chromosome the new chromosome
     */
    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    /**
     * Gets the start position.
     *
     * @return the start position
     */
    public long getStartPosition() {
        return startPosition;
    }

    /**
     * Sets the start position.
     *
     * @param startPosition the new start position
     */
    public void setStartPosition(long startPosition) {
        this.startPosition = startPosition;
    }

    /**
     * Gets the stop position.
     *
     * @return the stop position
     */
    public long getStopPosition() {
        return stopPosition;
    }

    /**
     * Sets the stop position.
     *
     * @param stopPosition the new stop position
     */
    public void setStopPosition(long stopPosition) {
        this.stopPosition = stopPosition;
    }

}
