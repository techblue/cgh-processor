package uk.co.techblue.cgh.dnap.configuration;

import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.DEFAULT_PROPERTIES;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.PROPERTIES_PATH;
import uk.co.techblue.cgh.dnap.annotation.PersistProperties;
import uk.co.techblue.cgh.dnap.annotation.Property;

// TODO: Auto-generated Javadoc
/**
 * The Class DefaultConfiguration.
 * @author dheeraj
 */
@PersistProperties(defaultPath = PROPERTIES_PATH + DEFAULT_PROPERTIES)
public class DefaultConfiguration implements IConfiguration {

    /** The fe params start offset. */
    @Property(name="feparams.start.offset")
    private String feParamsStartOffset; 
    
    /** The stats start offset. */
    @Property(name="stats.start.offset")
    private String statsStartOffset;
    
    /** The features start offset. */
    @Property(name="features.start.offset")
    private String featuresStartOffset;
    
    /** The loop break index. */
    @Property(name="loop.break.index")
    private String loopBreakIndex;

    /**
     * Gets the fe params start offset.
     *
     * @return the fe params start offset
     */
    public String getFeParamsStartOffset() {
        return feParamsStartOffset;
    }

    /**
     * Sets the fe params start offset.
     *
     * @param feParamsStartOffset the new fe params start offset
     */
    public void setFeParamsStartOffset(String feParamsStartOffset) {
        this.feParamsStartOffset = feParamsStartOffset;
    }

    /**
     * Gets the stats start offset.
     *
     * @return the stats start offset
     */
    public String getStatsStartOffset() {
        return statsStartOffset;
    }

    /**
     * Sets the stats start offset.
     *
     * @param statsStartOffset the new stats start offset
     */
    public void setStatsStartOffset(String statsStartOffset) {
        this.statsStartOffset = statsStartOffset;
    }

    /**
     * Gets the features start offset.
     *
     * @return the features start offset
     */
    public String getFeaturesStartOffset() {
        return featuresStartOffset;
    }

    /**
     * Sets the features start offset.
     *
     * @param featuresStartOffset the new features start offset
     */
    public void setFeaturesStartOffset(String featuresStartOffset) {
        this.featuresStartOffset = featuresStartOffset;
    }

    /**
     * Gets the loop break index.
     *
     * @return the loop break index
     */
    public String getLoopBreakIndex() {
        return loopBreakIndex;
    }

    /**
     * Sets the loop break index.
     *
     * @param loopBreakIndex the new loop break index
     */
    public void setLoopBreakIndex(String loopBreakIndex) {
        this.loopBreakIndex = loopBreakIndex;
    }
    
}
