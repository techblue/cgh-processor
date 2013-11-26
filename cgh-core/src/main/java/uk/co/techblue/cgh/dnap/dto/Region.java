package uk.co.techblue.cgh.dnap.dto;

/**
 * The Class Region.
 * @author dheeraj
 */
public class Region extends BaseDto{

    private String chromosome;
    
    private long startPosition;
    
    private long stopPosition;

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public long getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(long startPosition) {
        this.startPosition = startPosition;
    }

    public long getStopPosition() {
        return stopPosition;
    }

    public void setStopPosition(long stopPosition) {
        this.stopPosition = stopPosition;
    }
    
}
