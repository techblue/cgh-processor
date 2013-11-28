package uk.co.techblue.cgh.dnap.dao.impl;

import static uk.co.techblue.cgh.dnap.tables.Baselineaverages.BASELINEAVERAGES;
import static uk.co.techblue.cgh.dnap.tables.Region.REGION;
import static uk.co.techblue.cgh.dnap.tables.Regionintensity.REGIONINTENSITY;
import static uk.co.techblue.cgh.dnap.tables.Signal.SIGNAL;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;

import uk.co.techblue.cgh.dnap.dao.SignalProcessorDao;
import uk.co.techblue.cgh.dnap.tables.Array;
import uk.co.techblue.cgh.dnap.tables.Audit;
import uk.co.techblue.cgh.dnap.tables.Region;
import uk.co.techblue.cgh.dnap.tables.daos.ZscoreDao;
import uk.co.techblue.cgh.dnap.tables.pojos.Zscore;
import uk.co.techblue.cgh.dnap.tables.records.RegionRecord;
import uk.co.techblue.cgh.dnap.tables.records.RegionintensityRecord;
import uk.co.techblue.cgh.dnap.tables.records.SignalRecord;

// TODO: Auto-generated Javadoc
/**
 * The Class SignalProcessorDaoImpl.
 * @author dheeraj
 */
public class SignalProcessorDaoImpl implements SignalProcessorDao {

    /* (non-Javadoc)
     * @see uk.co.techblue.cgh.dnap.dao.SignalProcessorDao#getSignalData(org.jooq.Configuration, uk.co.techblue.cgh.dnap.tables.records.RegionRecord, java.lang.String)
     */
    @Override
    public List<SignalRecord> getSignalData(final Configuration configuration, final RegionRecord region, final String featureExtractorBarcode) {
        Result<?> signals = fetchSignalData(configuration, region, featureExtractorBarcode);
        List<SignalRecord> signalRecords = new ArrayList<SignalRecord>();
        for (Record signal : signals) {
            SignalRecord signalRecord = new SignalRecord();
            signalRecord.setFeatureextractorBarcode(signal.getValue(SIGNAL.FEATUREEXTRACTOR_BARCODE));
            signalRecord.setGprocessedsignal(signal.getValue(SIGNAL.GPROCESSEDSIGNAL));
            signalRecord.setRprocessedsignal(signal.getValue(SIGNAL.RPROCESSEDSIGNAL));
            signalRecord.setLogratio(signal.getValue(SIGNAL.LOGRATIO));
            signalRecords.add(signalRecord);
        }
        return signalRecords;
    }
    
    /**
     * Fetch signal data.
     *
     * @param configuration the configuration
     * @param region the region
     * @param featureExtractorBarcode the feature extractor barcode
     * @return the result
     */
    private Result<?> fetchSignalData(final Configuration configuration, final RegionRecord region, final String featureExtractorBarcode) {
        DSLContext dslCtx = DSL.using(configuration);
        return dslCtx
                .select(SIGNAL.FEATUREEXTRACTOR_BARCODE, SIGNAL.GPROCESSEDSIGNAL, SIGNAL.RPROCESSEDSIGNAL, SIGNAL.LOGRATIO)
                .from(SIGNAL)
                .where(SIGNAL.CHROMOSOME.equal(region.getChromosome()))
                .and(SIGNAL.FEATUREEXTRACTOR_BARCODE.equal(featureExtractorBarcode))
                .and(SIGNAL.STARTPOSITION.greaterThan(region.getStartposition()))
                .and(SIGNAL.STOPPOSITION.lessThan(region.getStopposition()))
                .andNot(SIGNAL.GPROCESSEDSIGNAL.greaterThan(6000.00))
                .andNot(SIGNAL.RPROCESSEDSIGNAL.greaterThan(6000.00))
                .andNot(SIGNAL.GISSATURATED.isTrue())
                .andNot(SIGNAL.RISSATURATED.isTrue())
                .andNot(SIGNAL.GISFEATNONUNIFOL.isTrue())
                .andNot(SIGNAL.RISFEATNONUNIFOL.isTrue())
                .andNot(SIGNAL.GISBGNONUNIFOL.isTrue())
                .andNot(SIGNAL.RISBGNONUNIFOL.isTrue())
                .andNot(SIGNAL.GPROCESSEDSIGNAL.lessThan(100.00).and(SIGNAL.RPROCESSEDSIGNAL.lessThan(100.00))).fetch();
    }
    
    /* (non-Javadoc)
     * @see uk.co.techblue.cgh.dnap.dao.SignalProcessorDao#getRegions(org.jooq.Configuration)
     */
    public List<RegionRecord> getRegions(final Configuration configuration) {
        Result<RegionRecord> regions = fetchRegions(configuration);
        List<RegionRecord> regionRecords = new ArrayList<RegionRecord>();
        for (RegionRecord region : regions) {
            regionRecords.add(region);
        }
        return regionRecords;
    }
    
    /**
     * Fetch regions.
     *
     * @param configuration the configuration
     * @return the result
     */
    private Result<RegionRecord> fetchRegions(final Configuration configuration){
        DSLContext dslCtx = DSL.using(configuration);
        return dslCtx.fetch(REGION);
    }
    
    /* (non-Javadoc)
     * @see uk.co.techblue.cgh.dnap.dao.SignalProcessorDao#getRegionIntensities(org.jooq.Configuration, uk.co.techblue.cgh.dnap.tables.records.RegionRecord)
     */
    @Override
    public List<RegionintensityRecord> getRegionIntensities(final Configuration configuration, final RegionRecord regionRecord) {
        DSLContext dslCtx = DSL.using(configuration);
        Result<?> regionIntensities = dslCtx.select().from(REGIONINTENSITY)
                .where(REGIONINTENSITY.REGIONID.equal(regionRecord.getRegionid()))
                .fetch();
        List<RegionintensityRecord> regionIntensityRecords = new ArrayList<RegionintensityRecord>();
        for (Record record : regionIntensities) {
            RegionintensityRecord regionIntensityRecord = new RegionintensityRecord();
            regionIntensityRecord.setRegionid(record.getValue(REGIONINTENSITY.REGIONID));
            regionIntensityRecord.setChromosome(record.getValue(REGIONINTENSITY.CHROMOSOME));
            regionIntensityRecord.setStartposition(record.getValue(REGIONINTENSITY.STARTPOSITION));
            regionIntensityRecord.setStopposition(record.getValue(REGIONINTENSITY.STOPPOSITION));
            regionIntensityRecord.setFeatureextractorBarcode(record.getValue(REGIONINTENSITY.FEATUREEXTRACTOR_BARCODE));
            regionIntensityRecord.setMeangreensignal(record.getValue(REGIONINTENSITY.MEANGREENSIGNAL));
            regionIntensityRecord.setMeanredsignal(record.getValue(REGIONINTENSITY.MEANREDSIGNAL));
            regionIntensityRecord.setMeanlogratio(record.getValue(REGIONINTENSITY.MEANLOGRATIO));
            regionIntensityRecord.setMediangreensignal(record.getValue(REGIONINTENSITY.MEDIANGREENSIGNAL));
            regionIntensityRecord.setMedianredsignal(record.getValue(REGIONINTENSITY.MEDIANREDSIGNAL));
            regionIntensityRecord.setMedianlogratio(record.getValue(REGIONINTENSITY.MEDIANLOGRATIO));
            regionIntensityRecords.add(regionIntensityRecord);
        }
        return regionIntensityRecords;
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.cgh.dnap.dao.SignalProcessorDao#getDistinctFeatureBarcodes(org.jooq.Configuration)
     */
    public List<String> getDistinctFeatureBarcodes(final Configuration configuration) {
        DSLContext dslCtx = DSL.using(configuration);
        return dslCtx.selectDistinct(Array.ARRAY.FEATUREEXTRACTOR_BARCODE).from(Array.ARRAY).fetch().getValues(Array.ARRAY.FEATUREEXTRACTOR_BARCODE);
    }
    
    /* (non-Javadoc)
     * @see uk.co.techblue.cgh.dnap.dao.SignalProcessorDao#saveZScores(org.jooq.Configuration)
     */
    @Override
    public void saveZScores(final Configuration configuration) {
        DSLContext dslCtx = DSL.using(configuration);
        List<Zscore> zScores = getCalculatedZScroes(dslCtx);
        new ZscoreDao(configuration).insert(zScores);
    }

    /**
     * Gets the calculated z scroes.
     *
     * @param dslCtx the dsl ctx
     * @return the calculated z scroes
     */
    private List<Zscore> getCalculatedZScroes(final DSLContext dslCtx){
        Result<?> zScoreResults = dslCtx.select(REGIONINTENSITY.REGIONINTENSITYID, 
                
                ((REGIONINTENSITY.MEANGREENSIGNAL.subtract(BASELINEAVERAGES.BMEANGREENSIGNAL)).divide(BASELINEAVERAGES.BMEANGREENSIGNALSD)).as("ZMeanGreenSignal"),
                ((REGIONINTENSITY.MEDIANGREENSIGNAL.subtract(BASELINEAVERAGES.BMEDIANGREENSIGNAL)).divide(BASELINEAVERAGES.BMEDIANGREENSIGNALSD)).as("ZMedianGreenSignal"),
                
                ((REGIONINTENSITY.MEANREDSIGNAL.subtract(BASELINEAVERAGES.BMEANREDSIGNAL)).divide(BASELINEAVERAGES.BMEANREDSIGNALSD)).as("ZMeanRedSignal"),
                ((REGIONINTENSITY.MEDIANREDSIGNAL.subtract(BASELINEAVERAGES.BMEDIANREDSIGNAL)).divide(BASELINEAVERAGES.BMEDIANREDSIGNALSD)).as("ZMedianRedSignal"),
                
                ((REGIONINTENSITY.MEANLOGRATIO.subtract(BASELINEAVERAGES.BMEANLOGRATIO)).divide(BASELINEAVERAGES.BMEANLOGRATIOSD)).as("ZMeanLogRatio"),
                ((REGIONINTENSITY.MEDIANLOGRATIO.subtract(BASELINEAVERAGES.BMEDIANLOGRATIO)).divide(BASELINEAVERAGES.BMEDIANLOGRATIOSD)).as("ZMedianLogRatio")
                
                ).from(REGIONINTENSITY).join(BASELINEAVERAGES).on(BASELINEAVERAGES.REGIONID.equal(REGIONINTENSITY.REGIONID)).fetch();

        List<Zscore> zScores = new ArrayList<Zscore>();
        for (Record zSoreRecord : zScoreResults) {
            Zscore zScore = new Zscore();
            zScore.setRegionintensityid(zSoreRecord.getValue(REGIONINTENSITY.REGIONINTENSITYID));
            zScore.setZmeangreensignal(NumberUtils.toDouble(zSoreRecord.getValue("ZMeanGreenSignal").toString()));
            zScore.setZmeanredsignal(NumberUtils.toDouble(zSoreRecord.getValue("ZMeanRedSignal").toString()));
            zScore.setZmeanlogratio(NumberUtils.toDouble(zSoreRecord.getValue("ZMeanLogRatio").toString()));
            zScore.setZmediangreensignal(NumberUtils.toDouble(zSoreRecord.getValue("ZMedianGreenSignal").toString()));
            zScore.setZmedianredsignal(NumberUtils.toDouble(zSoreRecord.getValue("ZMedianRedSignal").toString()));
            zScore.setZmedianlogratio(NumberUtils.toDouble(zSoreRecord.getValue("ZMedianLogRatio").toString()));
            zScores.add(zScore);
        }
        return zScores;
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.cgh.dnap.dao.SignalProcessorDao#deleteAllRegions(org.jooq.Configuration)
     */
    @Override
    public void deleteAllRegions(final Configuration configuration) {
        DSLContext dslCtx = DSL.using(configuration);
        dslCtx.delete(Region.REGION).execute();
    }

	/* (non-Javadoc)
	 * @see uk.co.techblue.cgh.dnap.dao.SignalProcessorDao#updateAudit(org.jooq.Configuration)
	 */
	@Override
	public void updateAudit(Configuration configuration) {
		DSLContext dslCtx = DSL.using(configuration);
		dslCtx.update(Audit.AUDIT).set(Audit.AUDIT.PROCESSED, Boolean.TRUE).execute();
	}
}
