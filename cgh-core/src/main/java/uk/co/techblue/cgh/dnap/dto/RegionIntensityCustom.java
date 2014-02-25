package uk.co.techblue.cgh.dnap.dto;

/**
 * The Class SignalProcessorWindow.
 * 
 * @author shiva agrawal
 */
public class RegionIntensityCustom {
    private Long arrayID;
    private Long regionID;
    private String sex;
    private String chromosome;
    private Long startPosition;
    private Long stopPosition;
    private Double meanGreenSignal;
    private Double medianGreenSignal;
    private Double meanRedSignal;
    private Double medianRedSignal;
    private Double meanLogRatio;
    private Double medianLogRatio;

    public Long getArrayID() {
        return arrayID;
    }

    public void setArrayID(Long arrayID) {
        this.arrayID = arrayID;
    }

    public Long getRegionID() {
        return regionID;
    }

    public void setRegionID(Long regionID) {
        this.regionID = regionID;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public Long getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Long startPosition) {
        this.startPosition = startPosition;
    }

    public Long getStopPosition() {
        return stopPosition;
    }

    public void setStopPosition(Long stopPosition) {
        this.stopPosition = stopPosition;
    }

    public Double getMeanGreenSignal() {
        return meanGreenSignal;
    }

    public void setMeanGreenSignal(Double meanGreenSignal) {
        this.meanGreenSignal = meanGreenSignal;
    }

    public Double getMedianGreenSignal() {
        return medianGreenSignal;
    }

    public void setMedianGreenSignal(Double medianGreenSignal) {
        this.medianGreenSignal = medianGreenSignal;
    }

    public Double getMeanRedSignal() {
        return meanRedSignal;
    }

    public void setMeanRedSignal(Double meanRedSignal) {
        this.meanRedSignal = meanRedSignal;
    }

    public Double getMedianRedSignal() {
        return medianRedSignal;
    }

    public void setMedianRedSignal(Double medianRedSignal) {
        this.medianRedSignal = medianRedSignal;
    }

    public Double getMeanLogRatio() {
        return meanLogRatio;
    }

    public void setMeanLogRatio(Double meanLogRatio) {
        this.meanLogRatio = meanLogRatio;
    }

    public Double getMedianLogRatio() {
        return medianLogRatio;
    }

    public void setMedianLogRatio(Double medianLogRatio) {
        this.medianLogRatio = medianLogRatio;
    }

}
