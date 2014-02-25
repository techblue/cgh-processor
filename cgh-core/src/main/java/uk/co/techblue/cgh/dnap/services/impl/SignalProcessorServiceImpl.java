package uk.co.techblue.cgh.dnap.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import uk.co.techblue.cgh.dnap.dao.SignalProcessorDao;
import uk.co.techblue.cgh.dnap.dao.impl.SignalProcessorDaoImpl;
import uk.co.techblue.cgh.dnap.dto.FeatureExtractorParameters;
import uk.co.techblue.cgh.dnap.dto.FeatureExtractorStatistics;
import uk.co.techblue.cgh.dnap.dto.Region;
import uk.co.techblue.cgh.dnap.dto.RegionIntensityCustom;
import uk.co.techblue.cgh.dnap.dto.SignalFeatures;
import uk.co.techblue.cgh.dnap.services.SignalProcessorService;
import uk.co.techblue.cgh.dnap.tables.daos.AuditDao;
import uk.co.techblue.cgh.dnap.tables.daos.BaselineaveragesDao;
import uk.co.techblue.cgh.dnap.tables.daos.RegionDao;
import uk.co.techblue.cgh.dnap.tables.daos.RegionintensityDao;
import uk.co.techblue.cgh.dnap.tables.daos.RegionintensityreferenceDao;
import uk.co.techblue.cgh.dnap.tables.daos.SignalDao;
import uk.co.techblue.cgh.dnap.tables.daos.SignalreferenceDao;
import uk.co.techblue.cgh.dnap.tables.pojos.Audit;
import uk.co.techblue.cgh.dnap.tables.pojos.Baselineaverages;
import uk.co.techblue.cgh.dnap.tables.pojos.Regionintensity;
import uk.co.techblue.cgh.dnap.tables.pojos.Regionintensityreference;
import uk.co.techblue.cgh.dnap.tables.pojos.Signal;
import uk.co.techblue.cgh.dnap.tables.pojos.Signalreference;
import uk.co.techblue.cgh.dnap.tables.records.ArrayRecord;
import uk.co.techblue.cgh.dnap.tables.records.ArrayreferenceRecord;
import uk.co.techblue.cgh.dnap.tables.records.RegionRecord;
import uk.co.techblue.cgh.dnap.tables.records.SignalRecord;
import uk.co.techblue.cgh.dnap.tables.records.SignalreferenceRecord;
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
    public long saveArray(final Configuration configuration, final FeatureExtractorParameters feParams,
            final FeatureExtractorStatistics feStats, String sex) {
        // Array array = getArrayRecord(feParams, feStats);

        DSLContext create = DSL.using(configuration);
        ArrayRecord record = create
                .insertInto(uk.co.techblue.cgh.dnap.tables.Array.ARRAY)
                .set(uk.co.techblue.cgh.dnap.tables.Array.ARRAY.FEATUREEXTRACTOR_BARCODE, feParams.getFeatureExtractorBarcode())
                .set(uk.co.techblue.cgh.dnap.tables.Array.ARRAY.SCAN_DATE, feParams.getScanDate())
                .set(uk.co.techblue.cgh.dnap.tables.Array.ARRAY.SHORTARRAYID, feParams.getShortArrayId())
                .set(uk.co.techblue.cgh.dnap.tables.Array.ARRAY.ANYCOLORPRCNTFEATNONUNIFOL,
                        feStats.getMetricAnyColorPrcntFeatNonUnifOL())
                .set(uk.co.techblue.cgh.dnap.tables.Array.ARRAY.DERIVATIVELR_SPREAD, feStats.getMetricDerivativeLRSpread())
                .set(uk.co.techblue.cgh.dnap.tables.Array.ARRAY.G_SIGNAL2NOISE, feStats.getMetricGSignal2Noise())
                .set(uk.co.techblue.cgh.dnap.tables.Array.ARRAY.G_SIGNALINTENSITY, feStats.getMetricGSignalIntensity())
                .set(uk.co.techblue.cgh.dnap.tables.Array.ARRAY.R_SIGNAL2NOISE, feStats.getMetricRSignal2Noise())
                .set(uk.co.techblue.cgh.dnap.tables.Array.ARRAY.R_SIGNALINTENSITY, feStats.getMetricRSignalIntensity())
                .set(uk.co.techblue.cgh.dnap.tables.Array.ARRAY.PROTOCOL_NAME, feParams.getProtocolName())
                .set(uk.co.techblue.cgh.dnap.tables.Array.ARRAY.GRID_GENOMICBUILD, feParams.getGrid_GenomicBuild())
                .set(uk.co.techblue.cgh.dnap.tables.Array.ARRAY.FEATUREEXTRACTOR_SCANFILENAME,
                        feParams.getFeatureExtractor_ScanFileName())
                .set(uk.co.techblue.cgh.dnap.tables.Array.ARRAY.FEATUREEXTRACTOR_DESIGNFILENAME,
                        feParams.getFeatureExtractor_DesignFileName()).set(uk.co.techblue.cgh.dnap.tables.Array.ARRAY.SEX, sex)
                .returning(uk.co.techblue.cgh.dnap.tables.Array.ARRAY.ARRAYID).fetchOne();
        if (record != null) {
            return record.getArrayid();
        }

        return 0l;

    }

    @Override
    public long saveArrayReference(final Configuration configuration, final FeatureExtractorParameters feParams,
            final FeatureExtractorStatistics feStats, String sex) {

        DSLContext create = DSL.using(configuration);
        ArrayreferenceRecord record = create
                .insertInto(uk.co.techblue.cgh.dnap.tables.Arrayreference.ARRAYREFERENCE)
                .set(uk.co.techblue.cgh.dnap.tables.Arrayreference.ARRAYREFERENCE.FEATUREEXTRACTOR_BARCODE,
                        feParams.getFeatureExtractorBarcode())
                .set(uk.co.techblue.cgh.dnap.tables.Arrayreference.ARRAYREFERENCE.SCAN_DATE, feParams.getScanDate())
                .set(uk.co.techblue.cgh.dnap.tables.Arrayreference.ARRAYREFERENCE.SHORTARRAYID, feParams.getShortArrayId())
                .set(uk.co.techblue.cgh.dnap.tables.Arrayreference.ARRAYREFERENCE.ANYCOLORPRCNTFEATNONUNIFOL,
                        feStats.getMetricAnyColorPrcntFeatNonUnifOL())
                .set(uk.co.techblue.cgh.dnap.tables.Arrayreference.ARRAYREFERENCE.DERIVATIVELR_SPREAD,
                        feStats.getMetricDerivativeLRSpread())
                .set(uk.co.techblue.cgh.dnap.tables.Arrayreference.ARRAYREFERENCE.G_SIGNAL2NOISE,
                        feStats.getMetricGSignal2Noise())
                .set(uk.co.techblue.cgh.dnap.tables.Arrayreference.ARRAYREFERENCE.G_SIGNALINTENSITY,
                        feStats.getMetricGSignalIntensity())
                .set(uk.co.techblue.cgh.dnap.tables.Arrayreference.ARRAYREFERENCE.R_SIGNAL2NOISE,
                        feStats.getMetricRSignal2Noise())
                .set(uk.co.techblue.cgh.dnap.tables.Arrayreference.ARRAYREFERENCE.R_SIGNALINTENSITY,
                        feStats.getMetricRSignalIntensity())
                .set(uk.co.techblue.cgh.dnap.tables.Arrayreference.ARRAYREFERENCE.PROTOCOL_NAME, feParams.getProtocolName())
                .set(uk.co.techblue.cgh.dnap.tables.Arrayreference.ARRAYREFERENCE.GRID_GENOMICBUILD,
                        feParams.getGrid_GenomicBuild())
                .set(uk.co.techblue.cgh.dnap.tables.Arrayreference.ARRAYREFERENCE.FEATUREEXTRACTOR_SCANFILENAME,
                        feParams.getFeatureExtractor_ScanFileName())
                .set(uk.co.techblue.cgh.dnap.tables.Arrayreference.ARRAYREFERENCE.FEATUREEXTRACTOR_DESIGNFILENAME,
                        feParams.getFeatureExtractor_DesignFileName())
                .set(uk.co.techblue.cgh.dnap.tables.Arrayreference.ARRAYREFERENCE.SEX, sex)
                .returning(uk.co.techblue.cgh.dnap.tables.Arrayreference.ARRAYREFERENCE.ARRAYID).fetchOne();
        if (record != null) {
            return record.getArrayid();
        }
        return 0l;

    }

    /* (non-Javadoc)
     * @see uk.co.techblue.cgh.dnap.services.SignalProcessorService#saveSignals(org.jooq.Configuration, java.lang.String, java.util.List)
     */
    @Override
    public void saveSignals(final Configuration configuration, final List<SignalFeatures> signalFeatures) {
        List<Signal> signals = null;
        if (signalFeatures != null && !signalFeatures.isEmpty()) {
            signals = new ArrayList<Signal>();
            for (SignalFeatures signalFeature : signalFeatures) {
                Signal signal = getSignalRecord(signalFeature);
                signals.add(signal);
            }
        }
        if (signals != null && !signals.isEmpty()) {
            new SignalDao(configuration).insert(signals);
        }
    }

    @Override
    public void saveSignalsReference(final Configuration configuration, final List<SignalFeatures> signalFeatures) {
        List<Signalreference> signals = null;
        if (signalFeatures != null && !signalFeatures.isEmpty()) {
            signals = new ArrayList<Signalreference>();
            for (SignalFeatures signalFeature : signalFeatures) {
                Signalreference signal = getSignalReferenceRecord(signalFeature);
                signals.add(signal);
            }
        }
        if (signals != null && !signals.isEmpty()) {
            new SignalreferenceDao(configuration).insert(signals);
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
    public List<SignalRecord> getSignalData(final Configuration configuration, final RegionRecord region, final Long arrayId) {
        return signalProcessorDao.getSignalData(configuration, region, arrayId);
    }

    @Override
    public List<SignalreferenceRecord> getSignalReferenceData(final Configuration configuration, final RegionRecord region,
            final Long arrayId) {
        return signalProcessorDao.getSignalReferenceData(configuration, region, arrayId);
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.cgh.dnap.services.SignalProcessorService#saveRegionIntensities(org.jooq.Configuration, java.util.List)
     */
    @Override
    public void saveRegionIntensities(final Configuration configuration, final List<Regionintensity> regionIntensities) {
        new RegionintensityDao(configuration).insert(regionIntensities);
    }

    @Override
    public void saveRegionIntensitiesReferences(final Configuration configuration,
            final List<Regionintensityreference> regionIntensities) {
        new RegionintensityreferenceDao(configuration).insert(regionIntensities);
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.cgh.dnap.services.SignalProcessorService#getRegionIntensities(org.jooq.Configuration, uk.co.techblue.cgh.dnap.tables.records.RegionRecord)
     */
    @Override
    public List<RegionIntensityCustom> getRegionIntensities(final Configuration configuration, final RegionRecord regionRecord) {
        return signalProcessorDao.getRegionIntensities(configuration, regionRecord);
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.cgh.dnap.services.SignalProcessorService#getDistinctFeatureBarcodes(org.jooq.Configuration)
     */
    @Override
    public List<Long> getDistinctFeatureBarcodes(final Configuration configuration) {
        return signalProcessorDao.getDistinctFeatureBarcodes(configuration);
    }

    @Override
    public List<Long> getDistinctFeatureBarcodesForReference(final Configuration configuration) {
        return signalProcessorDao.getDistinctFeatureBarcodesForRef(configuration);
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
//    private Array getArrayRecord(final FeatureExtractorParameters feParams, final FeatureExtractorStatistics feStats) {
//        Array array = new Array();
//        array.setFeatureextractorBarcode(feParams.getFeatureExtractorBarcode());
//        array.setScanDate(feParams.getScanDate());
//        array.setShortarrayid(feParams.getShortArrayId());
//        array.setAnycolorprcntfeatnonunifol(feStats.getMetricAnyColorPrcntFeatNonUnifOL());
//        array.setDerivativelrSpread(feStats.getMetricDerivativeLRSpread());
//        array.setGSignal2noise(feStats.getMetricGSignal2Noise());
//        array.setGSignalintensity(feStats.getMetricGSignalIntensity());
//        array.setRSignal2noise(feStats.getMetricRSignal2Noise());
//        array.setRSignalintensity(feStats.getMetricRSignalIntensity());
//        array.setProtocolName(feParams.getProtocolName());
//        array.setGridGenomicbuild(feParams.getGrid_GenomicBuild());
//        array.setFeatureextractorScanfilename(feParams.getFeatureExtractor_ScanFileName());
//        array.setFeatureextractorDesignfilename(feParams.getFeatureExtractor_DesignFileName());
//        return array;
//    }
//


    /**
     * Gets the signal record.
     *
     * @param featureExtractorBarcode the feature extractor barcode
     * @param feature the feature
     * @return the signal record
     */
    private Signal getSignalRecord(final SignalFeatures feature) {
        Signal signal = new Signal();
        signal.setChromosome(feature.getChromosome());
        signal.setGisbgnonunifol(feature.isGBGNonUnifOL());
        signal.setGisfeatnonunifol(feature.isGFeatNonUnifOL());
        signal.setGissaturated(feature.isGSaturated());
        signal.setGprocessedsignal(feature.getgProcessedSignal());
       // signal.setLogratio(feature.getLogRatio());
        signal.setLogratio(Math.log(feature.getrProcessedSignal() / feature.getgProcessedSignal()) / Math.log(2));
        signal.setProbename(feature.getProbeName());
        signal.setRisbgnonunifol(feature.isRBGNonUnifOL());
        signal.setRisfeatnonunifol(feature.isRFeatNonUnifOL());
        signal.setRissaturated(feature.isRSaturated());
        signal.setRprocessedsignal(feature.getrProcessedSignal());
        signal.setStartposition(feature.getStartPosition());
        signal.setStopposition(feature.getStopPosition());
        signal.setSystematicname(feature.getSystematicName());
        signal.setRiswellabovebg(Byte.parseByte(feature.getrIsWellAboveBG()));// (byte)1);//(byte)(feature.isrIsWellAboveBG()?1:0));
        signal.setGiswellabovebg(Byte.parseByte(feature.getgIsWellAboveBG()));// (feature.isgIsWellAboveBG()?1:0));
        signal.setArrayid(feature.getArrayId());
        return signal;
    }

    private Signalreference getSignalReferenceRecord(final SignalFeatures feature) {
        Signalreference signal = new Signalreference();
        signal.setChromosome(feature.getChromosome());
        signal.setGisbgnonunifol(feature.isGBGNonUnifOL());
        signal.setGisfeatnonunifol(feature.isGFeatNonUnifOL());
        signal.setGissaturated(feature.isGSaturated());
        signal.setGprocessedsignal(feature.getgProcessedSignal());
        // signal.setLogratio(feature.getLogRatio());
        signal.setLogratio(Math.log(feature.getrProcessedSignal() / feature.getgProcessedSignal()) / Math.log(2));
        signal.setProbename(feature.getProbeName());
        signal.setRisbgnonunifol(feature.isRBGNonUnifOL());
        signal.setRisfeatnonunifol(feature.isRFeatNonUnifOL());
        signal.setRissaturated(feature.isRSaturated());
        signal.setRprocessedsignal(feature.getrProcessedSignal());
        signal.setStartposition(feature.getStartPosition());
        signal.setStopposition(feature.getStopPosition());
        signal.setSystematicname(feature.getSystematicName());
        signal.setRiswellabovebg(Byte.parseByte(feature.getrIsWellAboveBG()));// (byte)1);//(byte)(feature.isrIsWellAboveBG()?1:0));
        signal.setGiswellabovebg(Byte.parseByte(feature.getgIsWellAboveBG()));// (feature.isgIsWellAboveBG()?1:0));
        signal.setArrayid(feature.getArrayId());
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
                regionPojo.setRegion(region.getRegionName());
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

    @Override
    public void deleteDatabase(Configuration configuration) {
        DSLContext dslCtx = DSL.using(configuration);
        dslCtx.truncate(uk.co.techblue.cgh.dnap.tables.Array.ARRAY).execute();
        dslCtx.truncate(uk.co.techblue.cgh.dnap.tables.Arrayreference.ARRAYREFERENCE).execute();
        dslCtx.truncate(uk.co.techblue.cgh.dnap.tables.Signal.SIGNAL).execute();
        dslCtx.truncate(uk.co.techblue.cgh.dnap.tables.Signalreference.SIGNALREFERENCE).execute();
        dslCtx.truncate(uk.co.techblue.cgh.dnap.tables.Regionintensity.REGIONINTENSITY).execute();
        dslCtx.truncate(uk.co.techblue.cgh.dnap.tables.Regionintensityreference.REGIONINTENSITYREFERENCE).execute();
        dslCtx.truncate(uk.co.techblue.cgh.dnap.tables.Baselineaverages.BASELINEAVERAGES).execute();
        dslCtx.truncate(uk.co.techblue.cgh.dnap.tables.Audit.AUDIT).execute();
        dslCtx.truncate(uk.co.techblue.cgh.dnap.tables.Zscore.ZSCORE).execute();

    }

}
