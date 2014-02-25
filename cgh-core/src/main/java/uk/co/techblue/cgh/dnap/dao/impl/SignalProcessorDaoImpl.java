package uk.co.techblue.cgh.dnap.dao.impl;

import static uk.co.techblue.cgh.dnap.tables.Baselineaverages.BASELINEAVERAGES;
import static uk.co.techblue.cgh.dnap.tables.Region.REGION;
import static uk.co.techblue.cgh.dnap.tables.Array.ARRAY;
import static uk.co.techblue.cgh.dnap.tables.Arrayreference.ARRAYREFERENCE;
import static uk.co.techblue.cgh.dnap.tables.Regionintensity.REGIONINTENSITY;
import static uk.co.techblue.cgh.dnap.tables.Regionintensityreference.REGIONINTENSITYREFERENCE;
import static uk.co.techblue.cgh.dnap.tables.Signal.SIGNAL;
import static uk.co.techblue.cgh.dnap.tables.Signalreference.SIGNALREFERENCE;

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
import uk.co.techblue.cgh.dnap.tables.Arrayreference;
import uk.co.techblue.cgh.dnap.tables.Audit;
import uk.co.techblue.cgh.dnap.tables.Region;
import uk.co.techblue.cgh.dnap.tables.daos.ZscoreDao;
import uk.co.techblue.cgh.dnap.tables.pojos.Zscore;
import uk.co.techblue.cgh.dnap.tables.records.RegionRecord;
import uk.co.techblue.cgh.dnap.tables.records.RegionintensityRecord;
import uk.co.techblue.cgh.dnap.tables.records.SignalRecord;
import uk.co.techblue.cgh.dnap.tables.records.SignalreferenceRecord;
import uk.co.techblue.cgh.dnap.dto.RegionIntensityCustom;

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
    public List<SignalRecord> getSignalData(final Configuration configuration, final RegionRecord region, final Long arrayId) {
        Result<?> signals = fetchSignalData(configuration, region, arrayId);
        List<SignalRecord> signalRecords = new ArrayList<SignalRecord>();
        for (Record signal : signals) {
            SignalRecord signalRecord = new SignalRecord();
//            signalRecord.setFeatureextractorBarcode(signal.getValue(SIGNAL.FEATUREEXTRACTOR_BARCODE));
            signalRecord.setArrayid(signal.getValue(SIGNAL.ARRAYID));
            signalRecord.setGprocessedsignal(signal.getValue(SIGNAL.GPROCESSEDSIGNAL));
            signalRecord.setRprocessedsignal(signal.getValue(SIGNAL.RPROCESSEDSIGNAL));
            signalRecord.setLogratio(signal.getValue(SIGNAL.LOGRATIO));
            signalRecords.add(signalRecord);
        }
        return signalRecords;
    }
    
    
    @Override
    public List<SignalreferenceRecord> getSignalReferenceData(final Configuration configuration, final RegionRecord region,
            final Long arrayId) {
        Result<?> signals = fetchSignalReferenceData(configuration, region, arrayId);
        List<SignalreferenceRecord> signalRecords = new ArrayList<SignalreferenceRecord>();
        for (Record signal : signals) {
            SignalreferenceRecord signalRecord = new SignalreferenceRecord();
            // signalRecord.setFeatureextractorBarcode(signal.getValue(SIGNAL.FEATUREEXTRACTOR_BARCODE));
            signalRecord.setArrayid(signal.getValue(SIGNALREFERENCE.ARRAYID));
            signalRecord.setGprocessedsignal(signal.getValue(SIGNALREFERENCE.GPROCESSEDSIGNAL));
            signalRecord.setRprocessedsignal(signal.getValue(SIGNALREFERENCE.RPROCESSEDSIGNAL));
            signalRecord.setLogratio(signal.getValue(SIGNALREFERENCE.LOGRATIO));
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
    private Result<?> fetchSignalData(final Configuration configuration, final RegionRecord region, final Long arrayId) {
        DSLContext dslCtx = DSL.using(configuration);
        return dslCtx.select(SIGNAL.ARRAYID, SIGNAL.GPROCESSEDSIGNAL, SIGNAL.RPROCESSEDSIGNAL, SIGNAL.LOGRATIO).from(SIGNAL)
                .where(SIGNAL.CHROMOSOME.equal(region.getChromosome())).and(SIGNAL.ARRAYID.equal(arrayId))
                .and(SIGNAL.STARTPOSITION.greaterThan(region.getStartposition()))
                .and(SIGNAL.STOPPOSITION.lessThan(region.getStopposition()))
                .andNot(SIGNAL.GPROCESSEDSIGNAL.greaterThan(6000.00)).andNot(SIGNAL.RPROCESSEDSIGNAL.greaterThan(6000.00))
                .andNot(SIGNAL.GISSATURATED.isTrue()).andNot(SIGNAL.RISSATURATED.isTrue())
                .andNot(SIGNAL.GISFEATNONUNIFOL.isTrue()).andNot(SIGNAL.RISFEATNONUNIFOL.isTrue())
                .andNot(SIGNAL.GISBGNONUNIFOL.isTrue()).andNot(SIGNAL.RISBGNONUNIFOL.isTrue())
                .andNot(SIGNAL.GPROCESSEDSIGNAL.lessThan(100.00).and(SIGNAL.RPROCESSEDSIGNAL.lessThan(100.00))).fetch();
    }
    
    private Result<?> fetchSignalReferenceData(final Configuration configuration, final RegionRecord region, final Long arrayId) {
        DSLContext dslCtx = DSL.using(configuration);
        return dslCtx
                .select(SIGNALREFERENCE.ARRAYID, SIGNALREFERENCE.GPROCESSEDSIGNAL, SIGNALREFERENCE.RPROCESSEDSIGNAL,
                        SIGNALREFERENCE.LOGRATIO)
                .from(SIGNALREFERENCE)
                .where(SIGNALREFERENCE.CHROMOSOME.equal(region.getChromosome()))
                .and(SIGNALREFERENCE.ARRAYID.equal(arrayId))
                .and(SIGNALREFERENCE.STARTPOSITION.greaterThan(region.getStartposition()))
                .and(SIGNALREFERENCE.STOPPOSITION.lessThan(region.getStopposition()))
                .andNot(SIGNALREFERENCE.GPROCESSEDSIGNAL.greaterThan(6000.00))
                .andNot(SIGNALREFERENCE.RPROCESSEDSIGNAL.greaterThan(6000.00))
                .andNot(SIGNALREFERENCE.GISSATURATED.isTrue())
                .andNot(SIGNALREFERENCE.RISSATURATED.isTrue())
                .andNot(SIGNALREFERENCE.GISFEATNONUNIFOL.isTrue())
                .andNot(SIGNALREFERENCE.RISFEATNONUNIFOL.isTrue())
                .andNot(SIGNALREFERENCE.GISBGNONUNIFOL.isTrue())
                .andNot(SIGNALREFERENCE.RISBGNONUNIFOL.isTrue())
                .andNot(SIGNALREFERENCE.GPROCESSEDSIGNAL.lessThan(100.00)
                        .and(SIGNALREFERENCE.RPROCESSEDSIGNAL.lessThan(100.00))).fetch();
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
    public List<RegionIntensityCustom> getRegionIntensities(final Configuration configuration, final RegionRecord regionRecord) {
        DSLContext dslCtx = DSL.using(configuration);
        Result<?> regionIntensities = dslCtx
                .select(REGIONINTENSITYREFERENCE.REGIONID, REGION.CHROMOSOME, REGIONINTENSITYREFERENCE.ARRAYID,
                        REGIONINTENSITYREFERENCE.MEANGREENSIGNAL, REGIONINTENSITYREFERENCE.MEANREDSIGNAL,
                        REGIONINTENSITYREFERENCE.MEANLOGRATIO, REGIONINTENSITYREFERENCE.MEDIANGREENSIGNAL,
                        REGIONINTENSITYREFERENCE.MEDIANREDSIGNAL, REGIONINTENSITYREFERENCE.MEDIANLOGRATIO, ARRAYREFERENCE.SEX)
                .from(REGIONINTENSITYREFERENCE.join(REGION).on(REGIONINTENSITYREFERENCE.REGIONID.equal(REGION.REGIONID))
                        .join(ARRAYREFERENCE).on(REGIONINTENSITYREFERENCE.ARRAYID.equal(ARRAYREFERENCE.ARRAYID)))
                .where(REGIONINTENSITYREFERENCE.REGIONID.equal(regionRecord.getRegionid())).fetch();
        List<RegionIntensityCustom> regionIntensityCustomRecords = new ArrayList<RegionIntensityCustom>();
        for (Record record : regionIntensities) {
            RegionIntensityCustom regionIntensityRecord = new RegionIntensityCustom();
            regionIntensityRecord.setRegionID(record.getValue(REGIONINTENSITYREFERENCE.REGIONID));
            regionIntensityRecord.setChromosome(record.getValue(REGION.CHROMOSOME));
            regionIntensityRecord.setArrayID(record.getValue(REGIONINTENSITYREFERENCE.ARRAYID));
            regionIntensityRecord.setMeanGreenSignal(record.getValue(REGIONINTENSITYREFERENCE.MEANGREENSIGNAL));
            regionIntensityRecord.setMeanRedSignal(record.getValue(REGIONINTENSITYREFERENCE.MEANREDSIGNAL));
            regionIntensityRecord.setMeanLogRatio(record.getValue(REGIONINTENSITYREFERENCE.MEANLOGRATIO));
            regionIntensityRecord.setMedianGreenSignal(record.getValue(REGIONINTENSITYREFERENCE.MEDIANGREENSIGNAL));
            regionIntensityRecord.setMedianRedSignal(record.getValue(REGIONINTENSITYREFERENCE.MEDIANREDSIGNAL));
            regionIntensityRecord.setMedianLogRatio(record.getValue(REGIONINTENSITYREFERENCE.MEDIANLOGRATIO));
            regionIntensityRecord.setSex(record.getValue(ARRAYREFERENCE.SEX));

            regionIntensityCustomRecords.add(regionIntensityRecord);
        }
        return regionIntensityCustomRecords;
    }

    /* (non-Javadoc)
     * @see uk.co.techblue.cgh.dnap.dao.SignalProcessorDao#getDistinctFeatureBarcodes(org.jooq.Configuration)
     */
    public List<Long> getDistinctFeatureBarcodes(final Configuration configuration) {
        DSLContext dslCtx = DSL.using(configuration);
        return dslCtx.selectDistinct(Array.ARRAY.ARRAYID).from(Array.ARRAY).fetch().getValues(Array.ARRAY.ARRAYID);
    }

    public List<Long> getDistinctFeatureBarcodesForRef(final Configuration configuration) {
        DSLContext dslCtx = DSL.using(configuration);
        return dslCtx.selectDistinct(Arrayreference.ARRAYREFERENCE.ARRAYID).from(Arrayreference.ARRAYREFERENCE).fetch()
                .getValues(Arrayreference.ARRAYREFERENCE.ARRAYID);
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

        Result<?> regionIntensitiesResults = dslCtx
                .select(REGION.CHROMOSOME, ARRAYREFERENCE.SEX, REGIONINTENSITYREFERENCE.MEANGREENSIGNAL,
                        REGIONINTENSITYREFERENCE.MEANREDSIGNAL, REGIONINTENSITYREFERENCE.MEANLOGRATIO,
                        REGIONINTENSITYREFERENCE.MEDIANGREENSIGNAL, REGIONINTENSITYREFERENCE.MEDIANREDSIGNAL,
                        REGIONINTENSITYREFERENCE.MEDIANLOGRATIO, BASELINEAVERAGES.BMEANGREENSIGNAL_M,
                        BASELINEAVERAGES.BMEANREDSIGNAL_M, BASELINEAVERAGES.BMEANLOGRATIO_M,
                        BASELINEAVERAGES.BMEDIANGREENSIGNAL_M, BASELINEAVERAGES.BMEDIANREDSIGNAL_M,
                        BASELINEAVERAGES.BMEDIANLOGRATIO_M, BASELINEAVERAGES.BMEANGREENSIGNALSD_M,
                        BASELINEAVERAGES.BMEANREDSIGNALSD_M, BASELINEAVERAGES.BMEANLOGRATIOSD_M,
                        BASELINEAVERAGES.BMEDIANGREENSIGNALSD_M, BASELINEAVERAGES.BMEDIANREDSIGNALSD_M,
                        BASELINEAVERAGES.BMEDIANLOGRATIOSD_M, BASELINEAVERAGES.BMEANGREENSIGNAL,
                        BASELINEAVERAGES.BMEANREDSIGNAL, BASELINEAVERAGES.BMEANLOGRATIO, BASELINEAVERAGES.BMEDIANGREENSIGNAL,
                        BASELINEAVERAGES.BMEDIANREDSIGNAL, BASELINEAVERAGES.BMEDIANLOGRATIO,
                        BASELINEAVERAGES.BMEANGREENSIGNALSD, BASELINEAVERAGES.BMEANREDSIGNALSD,
                        BASELINEAVERAGES.BMEANLOGRATIOSD, BASELINEAVERAGES.BMEDIANGREENSIGNALSD,
                        BASELINEAVERAGES.BMEDIANREDSIGNALSD, BASELINEAVERAGES.BMEDIANLOGRATIOSD,
                        REGIONINTENSITYREFERENCE.REGIONINTENSITYID)
                .from(REGIONINTENSITYREFERENCE.join(BASELINEAVERAGES)
                        .on(BASELINEAVERAGES.REGIONID.equal(REGIONINTENSITYREFERENCE.REGIONID)).join(ARRAYREFERENCE)
                        .on(REGIONINTENSITYREFERENCE.ARRAYID.equal(ARRAYREFERENCE.ARRAYID)).join(REGION)
                        .on(REGIONINTENSITYREFERENCE.REGIONID.equal(REGION.REGIONID))).fetch();

        List<Zscore> zScores = new ArrayList<Zscore>();

        for (Record rec : regionIntensitiesResults) {
            String chromosome = rec.getValue(REGION.CHROMOSOME);
            boolean isXorYChromosome = false;
            Zscore zScore = new Zscore();
            Double zMeanGreenSignal;
            Double zMeanRedSignal;
            Double zMeanLogRatio;
            Double zMedainGreenSignal;
            Double zMedianRedSignal;
            Double zMedianLogRatio;
            if (chromosome != null
                    && (chromosome.substring(3).equalsIgnoreCase("X") || chromosome.substring(3).equalsIgnoreCase("Y"))) {
                isXorYChromosome = true;
            }
            if (isXorYChromosome
                    && (rec.getValue(ARRAYREFERENCE.SEX) != null && rec.getValue(ARRAYREFERENCE.SEX).equalsIgnoreCase("M"))) {
                zMeanGreenSignal = getZScoreCore(rec.getValue(REGIONINTENSITYREFERENCE.MEANGREENSIGNAL),
                        rec.getValue(BASELINEAVERAGES.BMEANGREENSIGNAL_M), rec.getValue(BASELINEAVERAGES.BMEANGREENSIGNALSD_M));
                zMeanRedSignal = getZScoreCore(rec.getValue(REGIONINTENSITYREFERENCE.MEANREDSIGNAL),
                        rec.getValue(BASELINEAVERAGES.BMEANREDSIGNAL_M), rec.getValue(BASELINEAVERAGES.BMEANREDSIGNALSD_M));
                zMeanLogRatio = getZScoreCore(rec.getValue(REGIONINTENSITYREFERENCE.MEANLOGRATIO),
                        rec.getValue(BASELINEAVERAGES.BMEANLOGRATIO_M), rec.getValue(BASELINEAVERAGES.BMEANLOGRATIOSD_M));
                zMedainGreenSignal = getZScoreCore(rec.getValue(REGIONINTENSITYREFERENCE.MEDIANGREENSIGNAL),
                        rec.getValue(BASELINEAVERAGES.BMEDIANGREENSIGNAL_M),
                        rec.getValue(BASELINEAVERAGES.BMEDIANGREENSIGNALSD_M));
                zMedianRedSignal = getZScoreCore(rec.getValue(REGIONINTENSITYREFERENCE.MEDIANREDSIGNAL),
                        rec.getValue(BASELINEAVERAGES.BMEDIANREDSIGNAL_M), rec.getValue(BASELINEAVERAGES.BMEDIANREDSIGNALSD_M));
                zMedianLogRatio = getZScoreCore(rec.getValue(REGIONINTENSITYREFERENCE.MEDIANLOGRATIO),
                        rec.getValue(BASELINEAVERAGES.BMEDIANLOGRATIO_M), rec.getValue(BASELINEAVERAGES.BMEDIANLOGRATIOSD_M));
            } else {
                zMeanGreenSignal = getZScoreCore(rec.getValue(REGIONINTENSITYREFERENCE.MEANGREENSIGNAL),
                        rec.getValue(BASELINEAVERAGES.BMEANGREENSIGNAL), rec.getValue(BASELINEAVERAGES.BMEANGREENSIGNALSD));
                zMeanRedSignal = getZScoreCore(rec.getValue(REGIONINTENSITYREFERENCE.MEANREDSIGNAL),
                        rec.getValue(BASELINEAVERAGES.BMEANREDSIGNAL), rec.getValue(BASELINEAVERAGES.BMEANREDSIGNALSD));
                zMeanLogRatio = getZScoreCore(rec.getValue(REGIONINTENSITYREFERENCE.MEANLOGRATIO),
                        rec.getValue(BASELINEAVERAGES.BMEANLOGRATIO), rec.getValue(BASELINEAVERAGES.BMEANLOGRATIOSD));
                zMedainGreenSignal = getZScoreCore(rec.getValue(REGIONINTENSITYREFERENCE.MEDIANGREENSIGNAL),
                        rec.getValue(BASELINEAVERAGES.BMEDIANGREENSIGNAL), rec.getValue(BASELINEAVERAGES.BMEDIANGREENSIGNALSD));
                zMedianRedSignal = getZScoreCore(rec.getValue(REGIONINTENSITYREFERENCE.MEDIANREDSIGNAL),
                        rec.getValue(BASELINEAVERAGES.BMEDIANREDSIGNAL), rec.getValue(BASELINEAVERAGES.BMEDIANREDSIGNALSD));
                zMedianLogRatio = getZScoreCore(rec.getValue(REGIONINTENSITYREFERENCE.MEDIANLOGRATIO),
                        rec.getValue(BASELINEAVERAGES.BMEDIANLOGRATIO), rec.getValue(BASELINEAVERAGES.BMEDIANLOGRATIOSD));
            }
            if (zMeanGreenSignal != null) {
                zScore.setZmeangreensignal(zMeanGreenSignal);
            }
            if (zMeanRedSignal != null) {
                zScore.setZmeanredsignal(zMeanRedSignal);
            }
            if (zMeanLogRatio != null) {
                zScore.setZmeanlogratio(zMeanLogRatio);
            }
            if (zMedainGreenSignal != null) {
                zScore.setZmediangreensignal(zMedainGreenSignal);
            }
            if (zMedianRedSignal != null) {
                zScore.setZmedianredsignal(zMedianRedSignal);
            }
            if (zMedianLogRatio != null) {
                zScore.setZmedianlogratio(zMedianLogRatio);
            }
            zScore.setRegionintensityid(rec.getValue(REGIONINTENSITYREFERENCE.REGIONINTENSITYID));

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

    private Double getZScoreCore(Double regionIntensityValue, Double baseLineAverageValue, Double baseLineAverageSD) {
        double zScore;
        if (regionIntensityValue == null || baseLineAverageValue == null || baseLineAverageSD == null) {
            return null;
        } else {
            zScore = (regionIntensityValue - baseLineAverageValue) / baseLineAverageSD;
        }
        return zScore;
    }
}
