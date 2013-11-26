package uk.co.techblue.cgh.dnap.configuration;

import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.GENERAL_PROPERTIES;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.PROPERTIES_PATH;
import static uk.co.techblue.cgh.dnap.constant.SignalProcessorConstants.USER_PROPERTIES_PATH;
import uk.co.techblue.cgh.dnap.annotation.PersistProperties;
import uk.co.techblue.cgh.dnap.annotation.Property;
import uk.co.techblue.cgh.dnap.annotation.PersistProperties.ResourceBasePath;

// TODO: Auto-generated Javadoc
/**
 * The Class GeneralConfiguration.
 * @author dheeraj
 */
@PersistProperties(defaultPath = PROPERTIES_PATH + GENERAL_PROPERTIES, basePath = ResourceBasePath.USER_HOME, path = USER_PROPERTIES_PATH
        + GENERAL_PROPERTIES)
public class GeneralConfiguration implements IConfiguration {

    /** The attrib file name. */
    @Property(name = "attib.file", displayName = "Attribute File Name", description = "Name of attributes file")
    private String attribFileName;

    /** The region file name. */
    @Property(name = "region.file", displayName = "Regions File Name", description = "Name of regions file")
    private String regionFileName;

    /** The systematic const1. */
    @Property(name = "1_1", displayName = "Systematic constant 1_1", description = "Systematic constant 1_1")
    private String systematicConst1;

    /** The systematic const2. */
    @Property(name = "1_2", displayName = "Systematic constant 1_2", description = "Systematic constant 1_2")
    private String systematicConst2;

    /** The systematic const3. */
    @Property(name = "1_3", displayName = "Systematic constant 1_3", description = "Systematic constant 1_3")
    private String systematicConst3;

    /** The systematic const4. */
    @Property(name = "1_4", displayName = "Systematic constant 1_4", description = "Systematic constant 1_4")
    private String systematicConst4;

    /** The systematic const5. */
    @Property(name = "2_1", displayName = "Systematic constant 2_1", description = "Systematic constant 2_1")
    private String systematicConst5;

    /** The systematic const6. */
    @Property(name = "2_2", displayName = "Systematic constant 2_2", description = "Systematic constant 2_2")
    private String systematicConst6;

    /** The systematic const7. */
    @Property(name = "2_3", displayName = "Systematic constant 2_3", description = "Systematic constant 2_3")
    private String systematicConst7;

    /** The systematic const8. */
    @Property(name = "2_4", displayName = "Systematic constant 2_4", description = "Systematic constant 2_4")
    private String systematicConst8;

    /**
     * Gets the attrib file name.
     *
     * @return the attrib file name
     */
    public String getAttribFileName() {
        return attribFileName;
    }

    /**
     * Sets the attrib file name.
     *
     * @param attribFileName the new attrib file name
     */
    public void setAttribFileName(String attribFileName) {
        this.attribFileName = attribFileName;
    }

    /**
     * Gets the region file name.
     *
     * @return the region file name
     */
    public String getRegionFileName() {
        return regionFileName;
    }

    /**
     * Sets the region file name.
     *
     * @param regionFileName the new region file name
     */
    public void setRegionFileName(String regionFileName) {
        this.regionFileName = regionFileName;
    }

    /**
     * Gets the systematic const1.
     *
     * @return the systematic const1
     */
    public String getSystematicConst1() {
        return systematicConst1;
    }

    /**
     * Sets the systematic const1.
     *
     * @param systematicConst1 the new systematic const1
     */
    public void setSystematicConst1(String systematicConst1) {
        this.systematicConst1 = systematicConst1;
    }

    /**
     * Gets the systematic const2.
     *
     * @return the systematic const2
     */
    public String getSystematicConst2() {
        return systematicConst2;
    }

    /**
     * Sets the systematic const2.
     *
     * @param systematicConst2 the new systematic const2
     */
    public void setSystematicConst2(String systematicConst2) {
        this.systematicConst2 = systematicConst2;
    }

    /**
     * Gets the systematic const3.
     *
     * @return the systematic const3
     */
    public String getSystematicConst3() {
        return systematicConst3;
    }

    /**
     * Sets the systematic const3.
     *
     * @param systematicConst3 the new systematic const3
     */
    public void setSystematicConst3(String systematicConst3) {
        this.systematicConst3 = systematicConst3;
    }

    /**
     * Gets the systematic const4.
     *
     * @return the systematic const4
     */
    public String getSystematicConst4() {
        return systematicConst4;
    }

    /**
     * Sets the systematic const4.
     *
     * @param systematicConst4 the new systematic const4
     */
    public void setSystematicConst4(String systematicConst4) {
        this.systematicConst4 = systematicConst4;
    }

    /**
     * Gets the systematic const5.
     *
     * @return the systematic const5
     */
    public String getSystematicConst5() {
        return systematicConst5;
    }

    /**
     * Sets the systematic const5.
     *
     * @param systematicConst5 the new systematic const5
     */
    public void setSystematicConst5(String systematicConst5) {
        this.systematicConst5 = systematicConst5;
    }

    /**
     * Gets the systematic const6.
     *
     * @return the systematic const6
     */
    public String getSystematicConst6() {
        return systematicConst6;
    }

    /**
     * Sets the systematic const6.
     *
     * @param systematicConst6 the new systematic const6
     */
    public void setSystematicConst6(String systematicConst6) {
        this.systematicConst6 = systematicConst6;
    }

    /**
     * Gets the systematic const7.
     *
     * @return the systematic const7
     */
    public String getSystematicConst7() {
        return systematicConst7;
    }

    /**
     * Sets the systematic const7.
     *
     * @param systematicConst7 the new systematic const7
     */
    public void setSystematicConst7(String systematicConst7) {
        this.systematicConst7 = systematicConst7;
    }

    /**
     * Gets the systematic const8.
     *
     * @return the systematic const8
     */
    public String getSystematicConst8() {
        return systematicConst8;
    }

    /**
     * Sets the systematic const8.
     *
     * @param systematicConst8 the new systematic const8
     */
    public void setSystematicConst8(String systematicConst8) {
        this.systematicConst8 = systematicConst8;
    }
}
