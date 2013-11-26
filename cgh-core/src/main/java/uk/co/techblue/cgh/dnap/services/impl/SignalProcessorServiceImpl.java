package uk.co.techblue.cgh.dnap.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.jooq.Configuration;

import uk.co.techblue.cgh.dnap.dao.SignalProcessorDao;
import uk.co.techblue.cgh.dnap.dao.impl.SignalProcessorDaoImpl;
import uk.co.techblue.cgh.dnap.dto.FeatureExtractorParameters;
import uk.co.techblue.cgh.dnap.dto.FeatureExtractorStatistics;
import uk.co.techblue.cgh.dnap.dto.Region;
import uk.co.techblue.cgh.dnap.dto.SignalFeatures;
import uk.co.techblue.cgh.dnap.services.SignalProcessorService;
import uk.co.techblue.cgh.dnap.tables.daos.ArrayDao;
import uk.co.techblue.cgh.dnap.tables.daos.AuditDao;
import uk.co.techblue.cgh.dnap.tables.daos.BaselineaveragesDao;
import uk.co.techblue.cgh.dnap.tables.daos.RegionDao;
import uk.co.techblue.cgh.dnap.tables.daos.RegionintensityDao;
import uk.co.techblue.cgh.dnap.tables.daos.SignalDao;
import uk.co.techblue.cgh.dnap.tables.pojos.Array;
import uk.co.techblue.cgh.dnap.tables.pojos.Audit;
import uk.co.techblue.cgh.dnap.tables.pojos.Baselineaverages;
import uk.co.techblue.cgh.dnap.tables.pojos.Regionintensity;
import uk.co.techblue.cgh.dnap.tables.pojos.Signal;
import uk.co.techblue.cgh.dnap.tables.records.RegionRecord;
import uk.co.techblue.cgh.dnap.tables.records.RegionintensityRecord;
import uk.co.techblue.cgh.dnap.tables.records.SignalRecord;

// TODO: Auto-generated Javadoc
/**
 * The Class SignalProcessorServiceImpl.
 * @author dheeraj
 */
public class SignalProcessorServiceImpl implements SignalProcessorService {

    /** The signal processor dao. */
    private SignalProcessorDao signalProcessorDao = new SignalProcessorDaoImpl();

    /* (non-Javadoc)
     * @see uk.co.techblue.cgh.dnap.services.SignalProcessorService#saveArray(org.jooq.Configuration, uk.co.techblue.cgh.dnap.dto.FeatureExtractorParameters, uk.co.techblue.cgh.dnap.dto.FeatureExtractorStatistics)
     */
    @Override
    public void saveArray(final Configuration configuration, final FeatureExtractorParameters feParams, final FeatureExtractorStatistics feStats) {
        Array array = getArrayRecord(feParams, feStats);
        new ArrayDao(configuration).insert(array);
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.cgh.dnap.services.SignalProcessorService#saveSignals(org.jooq.Configuration, java.lang.String, java.util.List)
     */
    @Override
    public void saveSignals(final Configuration configuration, final String featureExtractorBarcode, final List<SignalFeatures> signalFeatures) {
        List<Signal> signals = null;
        if (signalFeatures != null && !signalFeatures.isEmpty()) {
            signals = new ArrayList<Signal>();
            for (SignalFeatures signalFeature : signalFeatures) {
                Signal signal = getSignalRecord(featureExtractorBarcode, signalFeature);
                signals.add(signal);
            }
        }
        if (signals != null && !signals.isEmpty()) {
            new SignalDao(configuration).insert(signals);
        }
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.cgh.dnap.services.SignalProcessorService#getRegions(org.jooq.Configuration)
     */
    @Override
    public List<RegionRecord> getRegions(final Configuration configuration) {
        return signalProcessorDao.getRegions(configuration);
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.cgh.dnap.services.SignalProcessorService#getSignalData(org.jooq.Configuration, uk.co.techblue.cgh.dnap.tables.records.RegionRecord, java.lang.String)
     */
    @Override
    public List<SignalRecord> getSignalData(final Configuration configuration, final RegionRecord region,
            final String featureExtractorBarcode) {
        return signalProcessorDao.getSignalData(configuration, region, featureExtractorBarcode);
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.cgh.dnap.services.SignalProcessorService#saveRegionIntensities(org.jooq.Configuration, java.util.List)
     */
    @Override
    public void saveRegionIntensities(final Configuration configuration, final List<Regionintensity> regionIntensities) {
        new RegionintensityDao(configuration).insert(regionIntensities);
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.cgh.dnap.services.SignalProcessorService#getRegionIntensities(org.jooq.Configuration, uk.co.techblue.cgh.dnap.tables.records.RegionRecord)
     */
    @Override
    public List<RegionintensityRecord> getRegionIntensities(final Configuration configuration, final RegionRecord regionRecord) {
        return signalProcessorDao.getRegionIntensities(configuration, regionRecord);
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.cgh.dnap.services.SignalProcessorService#getDistinctFeatureBarcodes(org.jooq.Configuration)
     */
    @Override
    public List<String> getDistinctFeatureBarcodes(final Configuration configuration) {
        return signalProcessorDao.getDistinctFeatureBarcodes(configuration);
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.cgh.dnap.services.SignalProcessorService#saveBaselineAverages(org.jooq.Configuration, java.util.List)
     */
    @Override
    public void saveBaselineAverages(final Configuration configuration, final List<Baselineaverages> baselineAverages) {
        new BaselineaveragesDao(configuration).insert(baselineAverages);
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.cgh.dnap.services.SignalProcessorService#saveZScores(org.jooq.Configuration)
     */
    @Override
    public void saveZScores(final Configuration configuration) {
        signalProcessorDao.saveZScores(configuration);
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.cgh.dnap.services.SignalProcessorService#saveRegions(org.jooq.Configuration, java.util.List)
     */
    @Override
    public void saveRegions(final Configuration configuration, final List<Region> regions) {
        List<uk.co.techblue.cgh.dnap.tables.pojos.Region> regionPojos = getRegionPojos(regions);
        new RegionDao(configuration).insert(regionPojos);
    }
    
    /**
     * Gets the array record.
     *
     * @param feParams the fe params
     * @param feStats the fe stats
     * @return the array record
     */
    private Array getArrayRecord(final FeatureExtractorParameters feParams, final FeatureExtractorStatistics feStats) {
        Array array = new Array();
        array.setFeatureextractorBarcode(feParams.getFeatureExtractorBarcode());
        array.setScanDate(feParams.getScanDate());
        array.setShortarrayid(feParams.getShortArrayId());
        array.setAnycolorprcntfeatnonunifol(feStats.getMetricAnyColorPrcntFeatNonUnifOL());
        array.setDerivativelrSpread(feStats.getMetricDerivativeLRSpread());
        array.setGSignal2noise(feStats.getMetricGSignal2Noise());
        array.setGSignalintensity(feStats.getMetricGSignalIntensity());
        array.setRSignal2noise(feStats.getMetricRSignal2Noise());
        array.setRSignalintensity(feStats.getMetricRSignalIntensity());
        return array;
    }

    /**
     * Gets the signal record.
     *
     * @param featureExtractorBarcode the feature extractor barcode
     * @param feature the feature
     * @return the signal record
     */
    private Signal getSignalRecord(final String featureExtractorBarcode, final SignalFeatures feature) {
        Signal signal = new Signal();
        signal.setChromosome(feature.getChromosome());
        signal.setFeatureextractorBarcode(featureExtractorBarcode);
        signal.setGisbgnonunifol(feature.isGBGNonUnifOL());
        signal.setGisfeatnonunifol(feature.isGFeatNonUnifOL());
        signal.setGissaturated(feature.isGSaturated());
        signal.setGprocessedsignal(feature.getgProcessedSignal());
        signal.setLogratio(feature.getLogRatio());
        signal.setProbename(feature.getProbeName());
        signal.setRisbgnonunifol(feature.isRBGNonUnifOL());
        signal.setRisfeatnonunifol(feature.isRFeatNonUnifOL());
        signal.setRissaturated(feature.isRSaturated());
        signal.setRprocessedsignal(feature.getrProcessedSignal());
        signal.setStartposition(feature.getStartPosition());
        signal.setStopposition(feature.getStopPosition());
        signal.setSystematicname(feature.getSystematicName());
        return signal;
    }
    
    /**
     * Gets the region pojos.
     *
     * @param regions the regions
     * @return the region pojos
     */
    private List<uk.co.techblue.cgh.dnap.tables.pojos.Region> getRegionPojos(final List<Region> regions) {
        List<uk.co.techblue.cgh.dnap.tables.pojos.Region> regionPojos = null;
        if (regions != null && !regions.isEmpty()) {
            regionPojos = new ArrayList<uk.co.techblue.cgh.dnap.tables.pojos.Region>();
            for (Region region : regions) {
                uk.co.techblue.cgh.dnap.tables.pojos.Region regionPojo = new uk.co.techblue.cgh.dnap.tables.pojos.Region();
                regionPojo.setChromosome(region.getChromosome());
                regionPojo.setStartposition(region.getStartPosition());
                regionPojo.setStopposition(region.getStopPosition());
                regionPojos.add(regionPojo);
            }
        }
        return regionPojos;
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.cgh.dnap.services.SignalProcessorService#truncateRegions(org.jooq.Configuration)
     */
    @Override
    public void truncateRegions(final Configuration configuration) {
        signalProcessorDao.deleteAllRegions(configuration);
    }

    /**
     * Gets the audit data.
     *
     * @param dataFileName the data file name
     * @return the audit data
     */
    private Audit getAuditData(final String dataFileName) {
    	Audit audit = new Audit();
    	audit.setFilename(dataFileName);    	
		return audit;
    }
    
	/* (non-Javadoc)
	 * @see uk.co.techblue.cgh.dnap.services.SignalProcessorService#auditDataFile(org.jooq.Configuration, java.lang.String)
	 */
	@Override
	public void auditDataFile(final Configuration configuration, final String dataFileName) {
		new AuditDao(configuration).insert(getAuditData(dataFileName));
	}

	/* (non-Javadoc)
	 * @see uk.co.techblue.cgh.dnap.services.SignalProcessorService#updateAudit(org.jooq.Configuration)
	 */
	@Override
	public void updateAudit(final Configuration configuration) {
		signalProcessorDao.updateAudit(configuration);
	}

}
