package uk.co.techblue.cgh.dnap.constant;

import uk.co.techblue.cgh.dnap.annotation.Property;

// TODO: Auto-generated Javadoc
/**
 * The Interface SignalProcessorConstants.
 * @author dheeraj 
 */
public interface SignalProcessorConstants {

    /** The user properties path. */
    String USER_PROPERTIES_PATH = "/CGHFEProcessor/";
    
    /** The cgh log path. */
    String CGH_LOG_PATH = "CGH_Logs/cghlog.log";
        
    /** The db changeset. */
    String DB_CHANGESET = "uk/co/techblue/cgh/dnap/db/changeset/changeset-master.xml";
    
    /** The jdbc password. */
    String DB_PASSWORD = "db.password";
    
    /** The properties path. */
    String PROPERTIES_PATH = "/uk/co/techblue/cgh/dnap/properties/";
    
    /** The default properties. */
    String DEFAULT_PROPERTIES = "cgh-default.properties";
    
    /** The general properties. */
    String GENERAL_PROPERTIES = "cgh-general.properties";
    
    /** The csv mapping properties. */
    String CSV_MAPPING_PROPERTIES = "cgh-csv-mapping.properties";
    
    /** The database properties. */
    String DATABASE_PROPERTIES = "cgh-database.properties";
    
    /** The system properties. */
    String SYSTEM_PROPERTIES = "cgh-system.properties";

    /** The feature extractor barcode. */
    String FEATURE_EXTRACTOR_BARCODE = "featureExtractorBarcode";
    
    /** The feature protoclolName. */
    String FEATURE_PROTOCOL_NAME = "protocolName";

    /** The feature GRID GENOMIC BUILD. */
    String GRID_GENOMIC_BUILD = "Grid_GenomicBuild";

    /** The feature Feature Extractor ScanFileName */
    String FEATURE_EXTRACTOR_SCANFILENAME = "FeatureExtractor_ScanFileName";

    /** The feature Feature Extractor DesignFileName */
    String FEATURE_EXTRACTOR_DESIGNFILENAME = "FeatureExtractor_DesignFileName";

    /** The scan date. */
    String SCAN_DATE = "scanDate";

    /** The metric any color prcnt feat non unif ol. */
    String METRIC_ANY_COLOR_PRCNT_FEAT_NON_UNIF_OL = "metricAnyColorPrcntFeatNonUnifOL";
    
    /** The metric derivative lr spread. */
    String METRIC_DERIVATIVE_LR_SPREAD = "metricDerivativeLRSpread";
    
    /** The METRI c_ g_ signa l2 noise. */
    String METRIC_G_SIGNAL2NOISE = "metricGSignal2Noise";
    
    /** The metric g signal intensity. */
    String METRIC_G_SIGNAL_INTENSITY = "metricGSignalIntensity";
    
    /** The METRI c_ r_ signa l2 noise. */
    String METRIC_R_SIGNAL2NOISE = "metricRSignal2Noise";
    
    /** The metric r signal intensity. */
    String METRIC_R_SIGNAL_INTENSITY = "metricRSignalIntensity";

    String GREEN_NUM_NON_UNIF_BGOL = "gNumNonUnifBGOL";

    String RED_NUM_NON_UNIF_BGOL = "rNumNonUnifBGOL";

    String TOTAL_NUM_FEATURES = "TotalNumFeatures";
    
    

    /** The probe name. */
    String PROBE_NAME = "probeName";
    
    /** The systematic name. */
    String SYSTEMATIC_NAME = "systematicName";
    
    /** The log ratio. */
    String LOG_RATIO = "logRatio";
    
    /** The g processed signal. */
    String G_PROCESSED_SIGNAL = "gProcessedSignal";
    
    /** The r processed signal. */
    String R_PROCESSED_SIGNAL = "rProcessedSignal";
    
    /** The g saturated. */
    String G_SATURATED = "isGSaturated";
    
    /** The r saturated. */
    String R_SATURATED = "isRSaturated";
    
    /** The g feat non unif ol. */
    String G_FEAT_NON_UNIF_OL = "isGFeatNonUnifOL";
    
    /** The r feat non unif ol. */
    String R_FEAT_NON_UNIF_OL = "isRFeatNonUnifOL";
    
    /** The g bg non unif ol. */
    String G_BG_NON_UNIF_OL = "isGBGNonUnifOL";
    
    /** The r bg non unif ol. */
    String R_BG_NON_UNIF_OL = "isRBGNonUnifOL";

    /** The array id. */
    String ARRAY_ID = "arrayId";
    
    /** The global display name. */
    String GLOBAL_DISPLAY_NAME = "globalDisplayName";
    
    /** The red sample. */
    String RED_SAMPLE = "redSample";
    
    /** The green sample. */
    String GREEN_SAMPLE = "greenSample";
    
    /** The polarity. */
    String POLARITY = "polarity";
    
    /** The SHOR t_ arra y_ i d_ cons t_1_1. */
    String SHORT_ARRAY_ID_CONST_1_1 = "1_1";
    
    /** The SHOR t_ arra y_ i d_ cons t_1_2. */
    String SHORT_ARRAY_ID_CONST_1_2 = "1_2";
    
    /** The SHOR t_ arra y_ i d_ cons t_1_3. */
    String SHORT_ARRAY_ID_CONST_1_3 = "1_3";
    
    /** The SHOR t_ arra y_ i d_ cons t_1_4. */
    String SHORT_ARRAY_ID_CONST_1_4 = "1_4";
    
    /** The SHOR t_ arra y_ i d_ cons t_2_1. */
    String SHORT_ARRAY_ID_CONST_2_1 = "2_1";
    
    /** The SHOR t_ arra y_ i d_ cons t_2_2. */
    String SHORT_ARRAY_ID_CONST_2_2 = "2_2";
    
    /** The SHOR t_ arra y_ i d_ cons t_2_3. */
    String SHORT_ARRAY_ID_CONST_2_3 = "2_3";
    
    /** The SHOR t_ arra y_ i d_ cons t_2_4. */
    String SHORT_ARRAY_ID_CONST_2_4 = "2_4";
    
    /** The watch dir path. */
    String WATCH_DIR_PATH = "watch.folder.path";
    
    /** The referenceData dir path. */
    String REF_DATA_DIR_PATH = "refwatch.folder.path";
    
    /** The archive dir path. */
    String ARCHIVE_DIR_PATH = "archive.folder.path";
    
    /** The toggle state. */
    String TOGGLE_STATE = "toggle.state";
    
    /** The dialog title. */
    String DIALOG_TITLE = "Signal Extraction Processor";
    
    /** The app running indicator. */
    String APP_RUNNING_INDICATOR = ".cghrunning";
    
    
    String APPLICATION_LOG_FILE = "CGH_Logs/cghlog.log";
    
    
    String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    
    String DB_DRIVER_PROP_NAME = "db.driver";

    String GREEN_IS_WELL_ABOVE_BG = "gIsWellAboveBG";

    String RED_IS_WELL_ABOVE_BG = "rIsWellAboveBG";

    String SEX = "sex";
}

