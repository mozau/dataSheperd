package com.datashepherd.service;

import com.datashepherd.exception.WorkbookException;

import java.io.InputStream;
import java.util.List;

public class ReaderService extends ExcelService {
    /**
     * Initializes the workbook with a template provided as an InputStream for XLSX format files.
     * This method sets the workbook type to XSSF and creates a new workbook instance based on the provided template InputStream.
     * It encapsulates the complexity of initializing a workbook with a template, making it easier to work with Excel files programmatically.
     * If any errors occur during the workbook creation, a WorkbookException is thrown.
     *
     * @param template The InputStream of the template file for initializing the workbook. Must not be null.
     * @return The current instance of ReaderService for method chaining.
     */
    public ReaderService xlsx(InputStream template){
        xlsxWork(template);
        return this;
    }

    /**
     * Initializes the workbook with a template file path for XLS format files.
     * This method sets the workbook type to HSSF and creates a new workbook instance by loading the template from the specified path.
     * It simplifies the process of working with Excel files by abstracting the initialization details.
     * If any errors occur during the workbook creation, a WorkbookException is thrown.
     *
     * @param templatePath The filesystem path to the template file. This file should exist and be accessible.
     * @return The current instance of ReaderService for method chaining.
     */
    public ReaderService xls(String templatePath){
        xlsWork(templatePath);
        return this;
    }

    /**
     * Initializes the workbook with a template provided as an InputStream for XLS format files.
     * This method sets the workbook type to HSSF and creates a new workbook instance based on the provided template InputStream.
     * It allows for the manipulation of older Excel files by abstracting the initialization details.
     * If any errors occur during the workbook creation, a WorkbookException is thrown.
     *
     * @param template The InputStream of the template file for initializing the workbook. Must not be null.
     * @return The current instance of ReaderService for method chaining.
     */
    public ReaderService xls(InputStream template){
        xlsWork(template);
        return this;
    }

    /**
     * Reads data from the Excel workbook into a list of type T.
     * This method leverages a generic Reader class to read data from the initialized workbook and return it as a list of objects of type T.
     * It requires that the workbook has been previously initialized and set. If the workbook is not set, a WorkbookException is thrown.
     * This method is designed to be flexible and can work with any type of data, provided that the data type T is specified and a corresponding
     * Reader class is available to handle the conversion from Excel rows to Java objects.
     *
     * @param <T> The type of the data to be read from the Excel workbook.
     * @param entityClass The class of the data type T, used for reflection in the Reader class to instantiate objects of type T.
     * @return A list of objects of type T read from the Excel workbook.
     * @throws WorkbookException If the workbook has not been set prior to calling this method.
     */
    public <T> List<T> readFromExcel(Class<T> entityClass) {
        if(workbook == null) {
            throw new WorkbookException("Workbook is not set");
        }
        Reader<T> reader = new Reader<>(workbook, entityClass);
        return reader.read();
    }
}
