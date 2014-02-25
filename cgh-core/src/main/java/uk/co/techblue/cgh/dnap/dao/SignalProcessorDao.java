package uk.co.techblue.cgh.dnap.dao;

import java.util.List;

import org.jooq.Configuration;

import uk.co.techblue.cgh.dnap.dto.RegionIntensityCustom;
import uk.co.techblue.cgh.dnap.tables.records.RegionRecord;
import uk.co.techblue.cgh.dnap.tables.records.SignalRecord;
import uk.co.techblue.cgh.dnap.tables.records.SignalreferenceRecord;

// TODO: Auto-generated Javadoc
/**
 * The Interface SignalProcessorDao.
 * @author dheeraj
 */
public interface SignalProcessorDao {
    
    /**
     * Gets the signal data.
     *
     * @param configuration the configuration
     * @param region the region
     * @param featureExtractorBarcode the feature extractor barcode
     * @return the signal data
     */
    List<SignalRecord> getSignalData(final Configuration configuration, final RegionRecord region, final Long arrayId);
    
    /**
     * Gets the regions.
     *
     * @param configuration the configuration
     * @return the regions
     */
    List<RegionRecord> getRegions(final Configuration configuration);
    
    /**
     * Gets the region intensities.
     *
     * @param configuration the configuration
     * @param regionRecord the region record
     * @return the region intensities
     */
    List<RegionIntensityCustom> getRegionIntensities(final Configuration configuration, final RegionRecord regionRecord);

    /**
     * Gets the distinct feature barcodes.
     *
     * @param configuration the configuration
     * @return the distinct feature barcodes
     */
    List<Long> getDistinctFeatureBarcodes(final Configuration configuration);

    /**
     * Save z scores.
     *
     * @param configuration the configuration
     */
    void saveZScores(final Configuration configuration);

    /**
     * Delete all regions.
     *
     * @param configuration the configuration
     */
    void deleteAllRegions(final Configuration configuration);

	/**
	 * Update audit.
	 *
	 * @param configuration the configuration
	 */
	void updateAudit(final Configuration configuration);

    List<SignalreferenceRecord> getSignalReferenceData(Configuration configuration, RegionRecord region, Long arrayId);

    List<Long> getDistinctFeatureBarcodesForRef(final Configuration configuration);

}
