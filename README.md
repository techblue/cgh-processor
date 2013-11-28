cgh-processor
=============

Array CGH Signal processor

A software to automate transfer and analysis of data generated by the “array CGH test”. 
Each array CGH test generates ~60,000 rows of data which are analyzed to identify regions of DNA that have been lost or gained and which may result in disease. 

Each “array” consists of “probes” that match different parts of the human genome. These probes indicate whether the patient has lost or gained DNA at each position. 

This software consist of 2 modules, 
###Module 1 (data analysis) - Identification of regions where both patients have lost or gained DNA
###Module 2 (data transfer) - Importing into sql server database (under progress...) 

##Software Functionality

###Module 1 -- Data Analysis

Module 1 consists of 4 main functions or sub modules to cover the data analysis functionality.

1.) WatcherService: This service is responsible for polling the watch directory in defined interval of time period. 
    The main task of this service is to track if there are the expected file(s) in the watch directory, pass the path of the file to data extraction service to parse the files to get required data.
    This service is particularly looking for 1 attribs file(having the list of all signal files), 1 regions file(having regions to process signal data) and Signal files.
    

2.) SignalExtractionProcessor: This is data extraction service responsible for parsing and extracting required data from TSV/CSV files.


3.) StartProcess: This sub module is responsible for processing of all extracted data and store the data in the database.
        
3.1) Save regions in the database in table `region`.
        
3.2) Process and save all arrays(FEStats) and signals(FEFeatures) in the database in tables, `array` and `signal` respectively.
        
3.3) Calculate Mean and Median of Red signals, Green Signals and Log ratio from `signal` table corresponding to each region and FE Extractor barcode and the results are stored in `regionintensity` table.
        
The signals will be fetched on the basis of certain query clause provided:
            
Signal Records:
    Feature extractor barcode = 252846911293_1_2
            
Region record:
    Chromosome = chr16
    Start position = 29500000
    Stop position = 30100000
            
            ```sql
            WHERE 
            Signal.Chromosome = chr16 AND
            Signal.StartPosition > 29500000 AND
            Signal.StopPosition < 30100000 AND
            NOT gProcessedSignal > 6000 AND
            NOT rProcessedSignal > 6000 AND
            NOT gSaturated = TRUE AND
            NOT rSaturated = TRUE AND
            NOT gIsFeatNonUnifOL = TRUE AND
            NOT rIsFeatNonUnifOL = TRUE AND
            NOT gIsBGNonUnifOL = TRUE AND
            NOT rIsBGNonUnifOL = TRUE AND
            NOT (gProcessedSignal < 100 and rProcessedSignal < 100) AND
            feExtractorBarCode = '252846911293_1_2'
            ``` 
            
            For the set of rows the following functions will be called for calculating Means and Medians.
            
            ```java
            calculateMean(gProcessedSignal)
            calculateMedian(gProcessedSignal)
            calculateMean(rProcessedSignal)
            calculateMedian(rProcessedSignal)
            calculateMean(logRatio)
            calculateMedian(logRatio)
            ```
            
3.4) In the next step; Mean, Median, MeanSD* and MedianSD* will be calculated of MeanRedsignal, MedianRedSignal, MeanGreenSignal, MedianGreenSignal, MeanLogRatio and MedionLogRaio of Region Intensities stored in `regionintensity` table corresponding to each region to get the base line averages. 
The calculated figures will be stored in `baselineaverages` table.
        
Baseline averages are calculated by further averaging the region intensities across all arrays(Feature extractor bar codes) corresponding to each region.
            
For the set of rows the following functions will handle the averaging:
            
            ```java
            calculateBaselineMean(meanGreenSignal)
            calculateBaselineMedian(meanGreenSignal)
            calculateBaselineMeanSD(meanGreenSignal)
            calculateBaselineMedianSD(meanGreenSignal)
            
            calculateBaselineMean(meanRedSignal)
            calculateBaselineMedian(meanRedSignal)
            calculateBaselineMeanSD(meanRedSignal)
            calculateBaselineMedianSD(meanRedSignal)
            
            calculateBaselineMean(meanLogRatio)
            calculateBaselineMedian(meanLogRatio)
            calculateBaselineMeanSD(meanLogRatio)
            calculateBaselineMedianSD(meanLogRatio)
            ```
            
        
3.5) In the final Step, ZScores are calculated on the basis of the formula provided and are stored in table `ZScroes`:
                
                ( RegionIntensity.MeanGreenSignal - BaselineAverages.BMeanGreenSignal )
                ------------------------------------------------------------------------
                                 BaselinAverages.BMeanGreenSignalSD 


4.) ProgressObserver: Progress observer service is responsible for observing the progress of complete module and log in the logger window provided on the UI to let user aware of the progress(Success or Failure) of the process. 

