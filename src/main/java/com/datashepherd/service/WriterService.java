package com.datashepherd.service;

import com.datashepherd.exception.WorkbookException;
import com.datashepherd.helper.WorkbookFactory;
import com.datashepherd.helper.WorkbookType;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.List;

public class WriterService extends ExcelService {
    /**
     * Initializes the workbook with a template provided as an InputStream for XLSX format files.
     * This method sets the workbook type to XSSF and creates a new workbook instance based on the provided template InputStream.
     * It encapsulates the complexity of initializing a workbook with a template, making it easier to work with Excel files programmatically.
     * If any errors occur during the workbook creation, a WorkbookException is thrown.
     *
     * @param template The InputStream of the template file for initializing the workbook. Must not be null.
     * @return The current instance of WriterService for method chaining.
     */
    public WriterService xlsx(InputStream template){
        xlsxWork(template);
        return this;
    }

    /**
     * Initializes the workbook with a template file path for XLSX format files.
     * This method sets the workbook type to XSSF and creates a new workbook instance by loading the template from the specified path.
     * It simplifies the process of working with Excel files by abstracting the initialization details.
     * If any errors occur during the workbook creation, a WorkbookException is thrown.
     *
     * @param templatePath The filesystem path to the template file. This file should exist and be accessible.
     * @return The current instance of WriterService for method chaining.
     */
    public WriterService xlsx(String templatePath){
        xlsxWork(templatePath);
        return this;
    }

    /**
     * Initializes the workbook with a template file path for XLS format files.
     * This method sets the workbook type to HSSF and creates a new workbook instance by loading the template from the specified path.
     * It is designed for working with older Excel files and simplifies the initialization process.
     * If any errors occur during the workbook creation, a WorkbookException is thrown.
     *
     * @param templatePath The filesystem path to the template file. This file should exist and be accessible.
     * @return The current instance of WriterService for method chaining.
     */
    public WriterService xls(String templatePath){
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
     * @return The current instance of WriterService for method chaining.
     */
    public WriterService xls(InputStream template){
        xlsWork(template);
        return this;
    }

    /**
     * Initializes the workbook as an XSSFWorkbook instance, which is suitable for reading and writing Excel files in the newer XLSX format.
     * If this method is not called explicitly, XSSFWorkbook will be used by default.
     *
     * @return The instance with the workbook initialized as XSSFWorkbook.
     * @throws WorkbookException If an error occurs while creating the XSSFWorkbook instance.
     */
    public WriterService xlsx() {
        try {
            type = WorkbookType.XSSF;
            workbook = WorkbookFactory.createWorkbook(type);
        } catch (ReflectiveOperationException e) {
            throw new WorkbookException("Error creating XSSFWorkbook instance",e);
        }
        return this;
    }

    /**
     * Initializes the workbook as an SXSSFWorkbook instance, which is suitable for reading and writing large ExcelService<T> files.
     *
     * @return The WriterService instance with the workbook initialized as SXSSFWorkbook.
     * @throws WorkbookException If an error occurs while creating the SXSSFWorkbook instance.
     */
    public WriterService xlsxLarge() {
        try {
            if(workbook == null) {
                throw new WorkbookException("XSSFWorkbook is not set, please call xlsx() or xlsx(InputStream template) first");
            }
            type = WorkbookType.SXSSF;
            workbook = WorkbookFactory.createWorkbook((XSSFWorkbook) workbook);
        } catch (ReflectiveOperationException e) {
            throw new WorkbookException("Error creating SXSSFWorkbook instance",e);
        }
        return this;
    }

    /**
     * Initializes the workbook as an SXSSFWorkbook instance, which is suitable for reading and writing large ExcelService<T> files.
     *
     * @return The WriterService instance with the workbook initialized as SXSSFWorkbook.
     * @throws WorkbookException If an error occurs while creating the SXSSFWorkbook instance.
     */
    public WriterService xlsxLargeWithTemplate() {
        try {
            type = WorkbookType.SXSSF;
            workbook = WorkbookFactory.createWorkbook((XSSFWorkbook) workbook);
        } catch (ReflectiveOperationException e) {
            throw new WorkbookException("Error creating SXSSFWorkbook instance, (NB) this function should called after xlsx function with parameter template",e);
        }
        return this;
    }

    /**
     * Initializes the workbook as an SXSSFWorkbook instance, which is suitable for reading and writing large ExcelService<T> files.
     *
     * @param rowAccessWindowSize The number of rows to keep in memory while streaming.
     * @param compressTmpFiles Whether temporary files should be compressed.
     * @return The WriterService instance with the workbook initialized as SXSSFWorkbook.
     * @throws WorkbookException If an error occurs while creating the SXSSFWorkbook instance.
     */
    public WriterService xlsxLargeWithTemplateAndRowAccessWindowSize(int rowAccessWindowSize, boolean compressTmpFiles) {
        try {
            type = WorkbookType.SXSSF;
            workbook = WorkbookFactory.createWorkbook((XSSFWorkbook) workbook,rowAccessWindowSize,compressTmpFiles);
        } catch (ReflectiveOperationException e) {
            throw new WorkbookException("Error creating SXSSFWorkbook instance, (NB) this function should called after xlsx function with parameter template",e);
        }
        return this;
    }

    /**
     * Initializes the workbook as an HSSFWorkbook instance, which is suitable for reading and writing ExcelService<T> files in the older XLS format.
     *
     * @return The WriterService instance with the workbook initialized as HSSFWorkbook.
     * @throws WorkbookException If an error occurs while creating the HSSFWorkbook instance.
     */
    public WriterService xls() {
        try {
            type = WorkbookType.HSSF;
            workbook = WorkbookFactory.createWorkbook(WorkbookType.HSSF);
        } catch (ReflectiveOperationException e) {
            throw new WorkbookException("Error creating HSSFWorkbook instance",e);
        }
        return this;
    }

    /**
     * Writes data to the Excel workbook.
     * This method takes a list of data of any type T and writes it to the workbook using a generic Writer class.
     * The method ensures that a workbook has been initialized before attempting to write data. If the workbook
     * is not set, it throws a WorkbookException. This method is designed to be flexible and work with any type
     * of data, provided that the data type T is specified. It leverages the Writer class to handle the specifics
     * of writing data to the workbook.
     *
     * @param <T> The type of the data to be written to the Excel workbook.
     * @param data A list of data items of type T to be written to the workbook.
     * @param entityClass The class of the data type T, used for reflection in the Writer class.
     * @return The current instance of WriterService, allowing for method chaining.
     * @throws WorkbookException If the workbook has not been set prior to calling this method.
     */
    public <T> WriterService writeToExcel(List<T> data, Class<T> entityClass) {
        if(workbook == null) {
            throw new WorkbookException("Workbook is not set");
        }
        new Writer<>(data, entityClass, workbook).write();
        return this;
    }
}
