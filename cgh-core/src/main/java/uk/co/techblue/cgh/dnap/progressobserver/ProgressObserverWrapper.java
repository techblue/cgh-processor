package uk.co.techblue.cgh.dnap.progressobserver;

import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class ProgressObserverWrapper.
 * @author dheeraj
 */
public class ProgressObserverWrapper {

    /** The progress observers. */
    private final List<ProgressObserver> progressObservers = new ArrayList<ProgressObserver>();
    
    /**
     * Adds the observer.
     *
     * @param progressObserver the progress observer
     */
    public void addObserver(ProgressObserver progressObserver) {
        progressObservers.add(progressObserver);
    }
    
    /**
     * Removes the all.
     */
    public void removeAll() {
        if(!progressObservers.isEmpty()){
            progressObservers.clear();
        }
    }
    
    /**
     * Publish progress start.
     */
    public void publishProgressStart() {
        for (ProgressObserver observer : progressObservers) {
            observer.publishProgressStart();
        }
    }
    
}
