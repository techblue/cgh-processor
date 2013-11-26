package uk.co.techblue.cgh.dnap.watcher;

public class ApplicationWatcher {
    
    private static boolean threadAlive;

    public static boolean isThreadAlive() {
        return threadAlive;
    }

    public static void setThreadAlive(boolean threadAlive) {
        ApplicationWatcher.threadAlive = threadAlive;
    }
    
}
