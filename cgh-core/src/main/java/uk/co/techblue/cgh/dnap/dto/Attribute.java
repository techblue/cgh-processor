package uk.co.techblue.cgh.dnap.dto;

/**
 * The Class Attribute.
 * @author dheeraj
 */
public class Attribute extends BaseDto {
    
    /** The array id. */
    private String arrayId;
    
    /** The global display name. */
    private String globalDisplayName ;
    
    /** The red sample. */
    private String redSample;
    
    /** The green sample. */
    private String greenSample;
    
    /** The polarity. */
    private String polarity;

    /** The sex of the person. */
    private String sex;

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

}
