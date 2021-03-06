/**
 * This class is generated by jOOQ
 */
package uk.co.techblue.cgh.dnap.tables.daos;

/**
 * This class is generated by jOOQ.
 */
@java.lang.SuppressWarnings({ "all", "unchecked" })
public class RegionintensityDao extends org.jooq.impl.DAOImpl<uk.co.techblue.cgh.dnap.tables.records.RegionintensityRecord, uk.co.techblue.cgh.dnap.tables.pojos.Regionintensity, java.lang.Long> {

	/**
	 * Create a new RegionintensityDao without any configuration
	 */
	public RegionintensityDao() {
		super(uk.co.techblue.cgh.dnap.tables.Regionintensity.REGIONINTENSITY, uk.co.techblue.cgh.dnap.tables.pojos.Regionintensity.class);
	}

	/**
	 * Create a new RegionintensityDao with an attached configuration
	 */
	public RegionintensityDao(org.jooq.Configuration configuration) {
		super(uk.co.techblue.cgh.dnap.tables.Regionintensity.REGIONINTENSITY, uk.co.techblue.cgh.dnap.tables.pojos.Regionintensity.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected java.lang.Long getId(uk.co.techblue.cgh.dnap.tables.pojos.Regionintensity object) {
		return object.getRegionintensityid();
	}

	/**
	 * Fetch records that have <code>RegionIntensityId IN (values)</code>
	 */
	public java.util.List<uk.co.techblue.cgh.dnap.tables.pojos.Regionintensity> fetchByRegionintensityid(java.lang.Long... values) {
		return fetch(uk.co.techblue.cgh.dnap.tables.Regionintensity.REGIONINTENSITY.REGIONINTENSITYID, values);
	}

	/**
	 * Fetch a unique record that has <code>RegionIntensityId = value</code>
	 */
	public uk.co.techblue.cgh.dnap.tables.pojos.Regionintensity fetchOneByRegionintensityid(java.lang.Long value) {
		return fetchOne(uk.co.techblue.cgh.dnap.tables.Regionintensity.REGIONINTENSITY.REGIONINTENSITYID, value);
	}

	/**
	 * Fetch records that have <code>RegionId IN (values)</code>
	 */
	public java.util.List<uk.co.techblue.cgh.dnap.tables.pojos.Regionintensity> fetchByRegionid(java.lang.Long... values) {
		return fetch(uk.co.techblue.cgh.dnap.tables.Regionintensity.REGIONINTENSITY.REGIONID, values);
	}

	/**
	 * Fetch records that have <code>MeanGreenSignal IN (values)</code>
	 */
	public java.util.List<uk.co.techblue.cgh.dnap.tables.pojos.Regionintensity> fetchByMeangreensignal(java.lang.Double... values) {
		return fetch(uk.co.techblue.cgh.dnap.tables.Regionintensity.REGIONINTENSITY.MEANGREENSIGNAL, values);
	}

	/**
	 * Fetch records that have <code>MedianGreenSignal IN (values)</code>
	 */
	public java.util.List<uk.co.techblue.cgh.dnap.tables.pojos.Regionintensity> fetchByMediangreensignal(java.lang.Double... values) {
		return fetch(uk.co.techblue.cgh.dnap.tables.Regionintensity.REGIONINTENSITY.MEDIANGREENSIGNAL, values);
	}

	/**
	 * Fetch records that have <code>MeanRedSignal IN (values)</code>
	 */
	public java.util.List<uk.co.techblue.cgh.dnap.tables.pojos.Regionintensity> fetchByMeanredsignal(java.lang.Double... values) {
		return fetch(uk.co.techblue.cgh.dnap.tables.Regionintensity.REGIONINTENSITY.MEANREDSIGNAL, values);
	}

	/**
	 * Fetch records that have <code>MedianRedSignal IN (values)</code>
	 */
	public java.util.List<uk.co.techblue.cgh.dnap.tables.pojos.Regionintensity> fetchByMedianredsignal(java.lang.Double... values) {
		return fetch(uk.co.techblue.cgh.dnap.tables.Regionintensity.REGIONINTENSITY.MEDIANREDSIGNAL, values);
	}

	/**
	 * Fetch records that have <code>MeanLogRatio IN (values)</code>
	 */
	public java.util.List<uk.co.techblue.cgh.dnap.tables.pojos.Regionintensity> fetchByMeanlogratio(java.lang.Double... values) {
		return fetch(uk.co.techblue.cgh.dnap.tables.Regionintensity.REGIONINTENSITY.MEANLOGRATIO, values);
	}

	/**
	 * Fetch records that have <code>MedianLogRatio IN (values)</code>
	 */
	public java.util.List<uk.co.techblue.cgh.dnap.tables.pojos.Regionintensity> fetchByMedianlogratio(java.lang.Double... values) {
		return fetch(uk.co.techblue.cgh.dnap.tables.Regionintensity.REGIONINTENSITY.MEDIANLOGRATIO, values);
	}

	/**
	 * Fetch records that have <code>ArrayId IN (values)</code>
	 */
	public java.util.List<uk.co.techblue.cgh.dnap.tables.pojos.Regionintensity> fetchByArrayid(java.lang.Long... values) {
		return fetch(uk.co.techblue.cgh.dnap.tables.Regionintensity.REGIONINTENSITY.ARRAYID, values);
	}
}
