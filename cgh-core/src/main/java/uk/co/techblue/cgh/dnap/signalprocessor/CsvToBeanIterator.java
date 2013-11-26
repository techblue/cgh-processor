package uk.co.techblue.cgh.dnap.signalprocessor;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.co.techblue.cgh.dnap.exception.CsvToBeanException;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.MappingStrategy;

/**
 * The Class CsvToBeanIterator.
 *
 * @param <T> the generic type
 * @author dheeraj
 */
public class CsvToBeanIterator<T> extends CsvToBean<T> {

    /** The csv reader. */
    private final CSVReader csvReader;

    /** The mapper. */
    private final MappingStrategy<T> mapper;

    /**
     * Instantiates a new csv to bean iterator.
     *
     * @param mapper the mapper
     * @param csv the csv
     */
    public CsvToBeanIterator(MappingStrategy<T> mapper, CSVReader csv) {
        this.csvReader = csv;
        this.mapper = mapper;
    }

    /**
     * Instantiates a new csv to bean iterator.
     *
     * @param mapper the mapper
     * @param reader the reader
     */
    public CsvToBeanIterator(MappingStrategy<T> mapper, Reader reader) {
        this.csvReader = new CSVReader(reader);
        this.mapper = mapper;
    }

    /* (non-Javadoc)
     * @see au.com.bytecode.opencsv.bean.CsvToBean#parse(au.com.bytecode.opencsv.bean.MappingStrategy, java.io.Reader)
     */
    public List<T> parse(MappingStrategy<T> mapper, Reader reader) {
        return parse(mapper, new CSVReader(reader));
    }

    /* (non-Javadoc)
     * @see au.com.bytecode.opencsv.bean.CsvToBean#parse(au.com.bytecode.opencsv.bean.MappingStrategy, au.com.bytecode.opencsv.CSVReader)
     */
    public List<T> parse(MappingStrategy<T> mapper, CSVReader csv) {
        try {
            mapper.captureHeader(csv);
            String[] line;
            List<T> list = new ArrayList<T>();
            while (null != (line = csv.readNext())) {
                T obj = processLine(mapper, line);
                list.add(obj);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("Error parsing CSV!", e);
        }
    }

    /**
     * Read next.
     *
     * @return the t
     * @throws CsvToBeanException the csv to bean exception
     */
    protected T readNext() throws CsvToBeanException {
        String[] line = null;
        try {
            line = csvReader.readNext();
        } catch (IOException ioe) {
            throw new CsvToBeanException("An error occurred while reading CSV at line " + Arrays.toString(line), ioe);
        }
        T obj = null;
        if (line != null) {
            try {
                obj = processLine(mapper, line);
            } catch (IllegalAccessException iae) {
                throw new CsvToBeanException("An error occurred while processing CSV at line " + Arrays.toString(line), iae);
            } catch (InvocationTargetException ite) {
                throw new CsvToBeanException("An error occurred while processing CSV at line " + Arrays.toString(line), ite);
            } catch (InstantiationException ie) {
                throw new CsvToBeanException("An error occurred while processing CSV at line " + Arrays.toString(line), ie);
            } catch (IntrospectionException ie) {
                throw new CsvToBeanException("An error occurred while processing CSV at line " + Arrays.toString(line), ie);
            }
        }
        return obj;
    }

}
