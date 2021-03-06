/**
 * This class is generated by jOOQ
 */
package uk.co.techblue.cgh.dnap.tables.interfaces;

/**
 * This class is generated by jOOQ.
 */
@java.lang.SuppressWarnings({ "all", "unchecked" })
public interface IArray extends java.io.Serializable {

	/**
	 * Setter for <code>cgh-processor.array.ArrayID</code>. 
	 */
	public void setArrayid(java.lang.Long value);

	/**
	 * Getter for <code>cgh-processor.array.ArrayID</code>. 
	 */
	public java.lang.Long getArrayid();

	/**
	 * Setter for <code>cgh-processor.array.FeatureExtractor_barcode</code>. 
	 */
	public void setFeatureextractorBarcode(java.lang.String value);

	/**
	 * Getter for <code>cgh-processor.array.FeatureExtractor_barcode</code>. 
	 */
	public java.lang.String getFeatureextractorBarcode();

	/**
	 * Setter for <code>cgh-processor.array.AnyColorPrcntFeatNonUnifOL</code>. 
	 */
	public void setAnycolorprcntfeatnonunifol(java.lang.Double value);

	/**
	 * Getter for <code>cgh-processor.array.AnyColorPrcntFeatNonUnifOL</code>. 
	 */
	public java.lang.Double getAnycolorprcntfeatnonunifol();

	/**
	 * Setter for <code>cgh-processor.array.DerivativeLR_spread</code>. 
	 */
	public void setDerivativelrSpread(java.lang.Double value);

	/**
	 * Getter for <code>cgh-processor.array.DerivativeLR_spread</code>. 
	 */
	public java.lang.Double getDerivativelrSpread();

	/**
	 * Setter for <code>cgh-processor.array.g_Signal2Noise</code>. 
	 */
	public void setGSignal2noise(java.lang.Double value);

	/**
	 * Getter for <code>cgh-processor.array.g_Signal2Noise</code>. 
	 */
	public java.lang.Double getGSignal2noise();

	/**
	 * Setter for <code>cgh-processor.array.g_SignalIntensity</code>. 
	 */
	public void setGSignalintensity(java.lang.Double value);

	/**
	 * Getter for <code>cgh-processor.array.g_SignalIntensity</code>. 
	 */
	public java.lang.Double getGSignalintensity();

	/**
	 * Setter for <code>cgh-processor.array.r_Signal2Noise</code>. 
	 */
	public void setRSignal2noise(java.lang.Double value);

	/**
	 * Getter for <code>cgh-processor.array.r_Signal2Noise</code>. 
	 */
	public java.lang.Double getRSignal2noise();

	/**
	 * Setter for <code>cgh-processor.array.r_SignalIntensity</code>. 
	 */
	public void setRSignalintensity(java.lang.Double value);

	/**
	 * Getter for <code>cgh-processor.array.r_SignalIntensity</code>. 
	 */
	public java.lang.Double getRSignalintensity();

	/**
	 * Setter for <code>cgh-processor.array.Scan_Date</code>. 
	 */
	public void setScanDate(java.lang.String value);

	/**
	 * Getter for <code>cgh-processor.array.Scan_Date</code>. 
	 */
	public java.lang.String getScanDate();

	/**
	 * Setter for <code>cgh-processor.array.ShortArrayId</code>. 
	 */
	public void setShortarrayid(java.lang.Double value);

	/**
	 * Getter for <code>cgh-processor.array.ShortArrayId</code>. 
	 */
	public java.lang.Double getShortarrayid();

	/**
	 * Setter for <code>cgh-processor.array.Protocol_Name</code>. 
	 */
	public void setProtocolName(java.lang.String value);

	/**
	 * Getter for <code>cgh-processor.array.Protocol_Name</code>. 
	 */
	public java.lang.String getProtocolName();

	/**
	 * Setter for <code>cgh-processor.array.Grid_GenomicBuild</code>. 
	 */
	public void setGridGenomicbuild(java.lang.String value);

	/**
	 * Getter for <code>cgh-processor.array.Grid_GenomicBuild</code>. 
	 */
	public java.lang.String getGridGenomicbuild();

	/**
	 * Setter for <code>cgh-processor.array.FeatureExtractor_ScanFileName</code>. 
	 */
	public void setFeatureextractorScanfilename(java.lang.String value);

	/**
	 * Getter for <code>cgh-processor.array.FeatureExtractor_ScanFileName</code>. 
	 */
	public java.lang.String getFeatureextractorScanfilename();

	/**
	 * Setter for <code>cgh-processor.array.FeatureExtractor_DesignFileName</code>. 
	 */
	public void setFeatureextractorDesignfilename(java.lang.String value);

	/**
	 * Getter for <code>cgh-processor.array.FeatureExtractor_DesignFileName</code>. 
	 */
	public java.lang.String getFeatureextractorDesignfilename();

	/**
	 * Setter for <code>cgh-processor.array.Sex</code>. 
	 */
	public void setSex(java.lang.String value);

	/**
	 * Getter for <code>cgh-processor.array.Sex</code>. 
	 */
	public java.lang.String getSex();

	// -------------------------------------------------------------------------
	// FROM and INTO
	// -------------------------------------------------------------------------

	/**
	 * Load data from another generated Record/POJO implementing the common interface IArray
	 */
	public void from(uk.co.techblue.cgh.dnap.tables.interfaces.IArray from);

	/**
	 * Copy data into another generated Record/POJO implementing the common interface IArray
	 */
	public <E extends uk.co.techblue.cgh.dnap.tables.interfaces.IArray> E into(E into);
}
