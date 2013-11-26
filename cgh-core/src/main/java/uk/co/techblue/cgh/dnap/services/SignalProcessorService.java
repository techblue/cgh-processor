package uk.co.techblue.cgh.dnap.services;

import java.util.List;

import org.jooq.Configuration;

import uk.co.techblue.cgh.dnap.dto.FeatureExtractorParameters;
import uk.co.techblue.cgh.dnap.dto.FeatureExtractorStatistics;
import uk.co.techblue.cgh.dnap.dto.Region;
import uk.co.techblue.cgh.dnap.dto.SignalFeatures;
import uk.co.techblue.cgh.dnap.tables.pojos.Baselineaverages;
import uk.co.techblue.cgh.dnap.tables.pojos.Regionintensity;
import uk.co.techblue.cgh.dnap.tables.records.RegionRecord;
import uk.co.techblue.cgh.dnap.tables.records.RegionintensityRecord;
import uk.co.techblue.cgh.dnap.tables.records.SignalRecord;

// TODO: Auto-generated Javadoc
/**
 * The Interface SignalProcessorService.
 * @author dheeraj
 * 
 */
public interface SignalProcessorService {

    /**
     * Save array.
     *
     * @param configuration the configuration
     * @param feParams the fe params
     * @param feStats the fe stats
     */
    void saveArray(final Configuration configuration, final FeatureExtractorParameters feParams, final FeatureExtractorStatistics feStats);

    /**
     * Save signals.
     *
     * @param configuration the configuration
     * @param featureExtractorBarcode the feature extractor barcode
     * @param signalFeatures the signal features
     */
    void saveSignals(final Configuration configuration, final String featureExtractorBarcode, final List<SignalFeatures> signalFeatures);

    /**
     * Gets the regions.
     *
     * @param configuration the configuration
     * @return the regions
     */
    List<RegionRecord> getRegions(final Configuration configuration);

    /**
     * Gets the signal data.
     *
     * @param configuration the configuration
     * @param region the region
     * @param featureExtractorBarcode the feature extractor barcode
     * @return the signal data
     */
    List<SignalRecord> getSignalData(final Configuration configuration, final RegionRecord region, final String featureExtractorBarcode);

    /**
     * Save region intensities.
     *
     * @param configuration the configuration
     * @param regionIntensities the region intensities
     */
    void saveRegionIntensities(final Configuration configuration, final List<Regionintensity> regionIntensities);

    /**
     * Gets the region intensities.
     *
     * @param configuration the configuration
     * @param regionRecord the region record
     * @return the region intensities
     */
    List<RegionintensityRecord> getRegionIntensities(final Configuration configuration, final RegionRecord regionRecord);

    /**
     * Gets the distinct feature barcodes.
     *
     * @param configuration the configuration
     * @return the distinct feature barcodes
     */
    List<String> getDistinctFeatureBarcodes(final Configuration configuration);

    /**
     * Save baseline averages.
     *
     * @param configuration the configuration
     * @param baselineAverages the baseline averages
     */
    void saveBaselineAverages(final Configuration configuration, final List<Baselineaverages> baselineAverages);

    /**
     * Save z scores.
     *
     * @param configuration the configuration
     */
    void saveZScores(final Configuration configuration);

    /**
     * Save regions.
     *
     * @param configuration the configuration
     * @param regions the regions
     */
    void saveRegions(final Configuration configuration, final List<Region> regions);

    /**
     * Truncate regions.
     *
     * @param configuration the configuration
     */
    void truncateRegions(final Configuration configuration);

	/**
	 * Audit data file.
	 *
	 * @param configuration the configuration
	 * @param dataFilePath the data file path
	 */
	void auditDataFile(final Configuration configuration, final String dataFilePath);

	/**
	 * Update audit.
	 *
	 * @param configuration the configuration
	 */
	void updateAudit(final Configuration configuration);

}
