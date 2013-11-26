/**
 * This class is generated by jOOQ
 */
package uk.co.techblue.cgh.dnap.tables.daos;

/**
 * This class is generated by jOOQ.
 */
@java.lang.SuppressWarnings({ "all", "unchecked" })
public class ArrayDao extends org.jooq.impl.DAOImpl<uk.co.techblue.cgh.dnap.tables.records.ArrayRecord, uk.co.techblue.cgh.dnap.tables.pojos.Array, java.lang.Long> {

	/**
	 * Create a new ArrayDao without any configuration
	 */
	public ArrayDao() {
		super(uk.co.techblue.cgh.dnap.tables.Array.ARRAY, uk.co.techblue.cgh.dnap.tables.pojos.Array.class);
	}

	/**
	 * Create a new ArrayDao with an attached configuration
	 */
	public ArrayDao(org.jooq.Configuration configuration) {
		super(uk.co.techblue.cgh.dnap.tables.Array.ARRAY, uk.co.techblue.cgh.dnap.tables.pojos.Array.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected java.lang.Long getId(uk.co.techblue.cgh.dnap.tables.pojos.Array object) {
		return object.getArrayid();
	}

	/**
	 * Fetch records that have <code>ArrayID IN (values)</code>
	 */
	public java.util.List<uk.co.techblue.cgh.dnap.tables.pojos.Array> fetchByArrayid(java.lang.Long... values) {
		return fetch(uk.co.techblue.cgh.dnap.tables.Array.ARRAY.ARRAYID, values);
	}

	/**
	 * Fetch a unique record that has <code>ArrayID = value</code>
	 */
	public uk.co.techblue.cgh.dnap.tables.pojos.Array fetchOneByArrayid(java.lang.Long value) {
		return fetchOne(uk.co.techblue.cgh.dnap.tables.Array.ARRAY.ARRAYID, value);
	}

	/**
	 * Fetch records that have <code>FeatureExtractor_barcode IN (values)</code>
	 */
	public java.util.List<uk.co.techblue.cgh.dnap.tables.pojos.Array> fetchByFeatureextractorBarcode(java.lang.String... values) {
		return fetch(uk.co.techblue.cgh.dnap.tables.Array.ARRAY.FEATUREEXTRACTOR_BARCODE, values);
	}

	/**
	 * Fetch records that have <code>AnyColorPrcntFeatNonUnifOL IN (values)</code>
	 */
	public java.util.List<uk.co.techblue.cgh.dnap.tables.pojos.Array> fetchByAnycolorprcntfeatnonunifol(java.lang.Double... values) {
		return fetch(uk.co.techblue.cgh.dnap.tables.Array.ARRAY.ANYCOLORPRCNTFEATNONUNIFOL, values);
	}

	/**
	 * Fetch records that have <code>DerivativeLR_spread IN (values)</code>
	 */
	public java.util.List<uk.co.techblue.cgh.dnap.tables.pojos.Array> fetchByDerivativelrSpread(java.lang.Double... values) {
		return fetch(uk.co.techblue.cgh.dnap.tables.Array.ARRAY.DERIVATIVELR_SPREAD, values);
	}

	/**
	 * Fetch records that have <code>g_Signal2Noise IN (values)</code>
	 */
	public java.util.List<uk.co.techblue.cgh.dnap.tables.pojos.Array> fetchByGSignal2noise(java.lang.Double... values) {
		return fetch(uk.co.techblue.cgh.dnap.tables.Array.ARRAY.G_SIGNAL2NOISE, values);
	}

	/**
	 * Fetch records that have <code>g_SignalIntensity IN (values)</code>
	 */
	public java.util.List<uk.co.techblue.cgh.dnap.tables.pojos.Array> fetchByGSignalintensity(java.lang.Double... values) {
		return fetch(uk.co.techblue.cgh.dnap.tables.Array.ARRAY.G_SIGNALINTENSITY, values);
	}

	/**
	 * Fetch records that have <code>r_Signal2Noise IN (values)</code>
	 */
	public java.util.List<uk.co.techblue.cgh.dnap.tables.pojos.Array> fetchByRSignal2noise(java.lang.Double... values) {
		return fetch(uk.co.techblue.cgh.dnap.tables.Array.ARRAY.R_SIGNAL2NOISE, values);
	}

	/**
	 * Fetch records that have <code>r_SignalIntensity IN (values)</code>
	 */
	public java.util.List<uk.co.techblue.cgh.dnap.tables.pojos.Array> fetchByRSignalintensity(java.lang.Double... values) {
		return fetch(uk.co.techblue.cgh.dnap.tables.Array.ARRAY.R_SIGNALINTENSITY, values);
	}

	/**
	 * Fetch records that have <code>Scan_Date IN (values)</code>
	 */
	public java.util.List<uk.co.techblue.cgh.dnap.tables.pojos.Array> fetchByScanDate(java.lang.String... values) {
		return fetch(uk.co.techblue.cgh.dnap.tables.Array.ARRAY.SCAN_DATE, values);
	}

	/**
	 * Fetch records that have <code>ShortArrayId IN (values)</code>
	 */
	public java.util.List<uk.co.techblue.cgh.dnap.tables.pojos.Array> fetchByShortarrayid(java.lang.Double... values) {
		return fetch(uk.co.techblue.cgh.dnap.tables.Array.ARRAY.SHORTARRAYID, values);
	}
}