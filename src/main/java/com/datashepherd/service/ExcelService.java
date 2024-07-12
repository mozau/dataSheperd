package com.datashepherd.service;

import com.datashepherd.exception.WorkbookException;
import com.datashepherd.helper.WorkbookFactory;
import com.datashepherd.helper.WorkbookType;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Objects;
import java.util.Optional;

class ExcelService {
    protected Workbook workbook;
    protected WorkbookType type;

    /**
     * Initializes the workbook with a template provided as an InputStream, specifically for XLSX format files.
     * This method prepares the workbook for operations on Excel files in the newer XLSX format by setting the
     * workbook type to XSSF and creating a new workbook instance based on the provided template InputStream.
     * It ensures that the input template is not null, throwing an InvalidPropertiesFormatException if it is.
     * This method encapsulates the complexity of initializing a workbook with a template, making it easier to
     * work with Excel files programmatically. If any errors occur during the workbook creation, such as issues
     * with the template InputStream or reflective operations required for XSSFWorkbook instantiation, a
     * WorkbookException is thrown.
     *
     * @param template The InputStream of the template file for initializing the workbook. Must not be null.
     * @throws WorkbookException If an error occurs during the creation of the XSSFWorkbook instance, encapsulated
     *                           in a WorkbookException with details of the underlying cause.
     */
    public void xlsxWork(InputStream template) {
        try {
            if(Objects.isNull(template)) throw new InvalidPropertiesFormatException("template input should not be null");
            type = WorkbookType.XSSF;
            workbook = WorkbookFactory.createWorkbook(WorkbookType.XSSF,template);
        } catch (InvalidPropertiesFormatException | ReflectiveOperationException e) {
            throw new WorkbookException("Error creating XSSFWorkbook instance with the template input stream",e);
        }
    }

    /**
     * Initializes the workbook with a template file as an XSSFWorkbook instance, suitable for handling
     * Excel files in the newer XLSX format. This method allows for the creation of a workbook based on
     * a template, facilitating the reading and writing of Excel files. It ensures that the workbook is
     * initialized with the XSSF (XML Spreadsheet Format) model, which is optimized for the .xlsx format.
     * If the template parameter is null, an InvalidPropertiesFormatException is thrown to indicate the
     * error. This method encapsulates the complexity of workbook creation, making it easier to work with
     * Excel files in Java applications.
     *
     * @param template The path to the template file. It must not be null and should be a valid path to an
     *                 Excel file in the .xlsx format.
     * @throws WorkbookException If an error occurs during the creation of the XSSFWorkbook instance, including
     *                           issues related to file access or internal errors during workbook initialization.
     */
    public void xlsxWork(String template) {
        try {
            if(Objects.isNull(template)) throw new InvalidPropertiesFormatException("template input should not be null");
            type = WorkbookType.XSSF;
            workbook = WorkbookFactory.createWorkbook(WorkbookType.XSSF,template);
        } catch (InvalidPropertiesFormatException | ReflectiveOperationException e) {
            throw new WorkbookException("Error creating XSSFWorkbook instance with the template input stream",e);
        }
    }

    /**
     * Initializes the workbook with a template file as an HSSFWorkbook instance. This method is designed
     * for working with Excel files in the older XLS format. It sets the workbook type to HSSF (indicating
     * the use of the HSSF model for Excel files) and creates a new workbook instance by opening an InputStream
     * to the specified template file. This approach allows for reading and potentially modifying existing
     * Excel files in the XLS format. If any errors occur during the opening of the InputStream or the creation
     * of the workbook, a WorkbookException is thrown, encapsulating the underlying issue.
     *
     * @param templatePath The filesystem path to the template file. This file should exist and be accessible.
     * @throws WorkbookException If an IOException is encountered when opening the InputStream to the template file,
     *                           or if a ReflectiveOperationException occurs during the creation of the HSSFWorkbook instance.
     *                           These exceptions are caught and rethrown as a WorkbookException with an appropriate message,
     *                           including the path to the problematic template file for easier troubleshooting.
     */
    public void xlsWork(String templatePath) {
        try {
            InputStream inputStream = FileUtils.openInputStream(new File(templatePath));
            type = WorkbookType.HSSF;
            workbook = WorkbookFactory.createWorkbook(WorkbookType.HSSF,inputStream);
        } catch (ReflectiveOperationException | IOException e) {
            throw new WorkbookException("Error creating HSSFWorkbook instance with templatePath ".concat(templatePath),e);
        }
    }

    /**
     * Initializes the workbook with a given template as an HSSFWorkbook instance. This method is suitable
     * for reading and writing Excel files in the older XLS format. It sets the workbook type to HSSF and
     * creates a new workbook instance using the provided InputStream template. If the creation process encounters
     * any reflective operation exceptions, it throws a WorkbookException with the error details.
     *
     * @param template The InputStream of the template file to be used for initializing the workbook.
     *                 Must not be null.
     * @throws WorkbookException If a ReflectiveOperationException occurs during the creation of the HSSFWorkbook instance.
     */
    public void xlsWork(InputStream template) {
        try {
            type = WorkbookType.HSSF;
            workbook = WorkbookFactory.createWorkbook(WorkbookType.HSSF,template);
        } catch (ReflectiveOperationException e) {
            throw new WorkbookException("Error creating HSSFWorkbook instance with template",e);
        }
    }

    /**
     * Saves the current workbook to the specified path. If the path does not end with the appropriate
     * file extension for the workbook type (XLSX for XSSFWorkbook, XLS for HSSFWorkbook), the method
     * automatically appends the correct extension. This method ensures that the workbook is saved
     * to the file system at the desired location. If the file does not exist, it will be created.
     * After saving the workbook, it is also closed to release resources.
     *
     * @param path The file system path where the workbook should be saved. This can be an absolute
     *             or relative path. If the path does not include the file extension, it will be
     *             automatically appended based on the workbook type.
     * @throws WorkbookException If an IOException occurs during file writing or if there is an error
     *                           saving the workbook to the file, encapsulated in a WorkbookException.
     */
    public void saveExcelTo(String path) {
        String pathname = StringUtils.contains(path, type.getFileExtension()) ? path : path.concat(".").concat(type.getFileExtension());
        try (FileOutputStream fileOut =  new FileOutputStream(Optional.ofNullable(FileUtils.getFile(pathname)).orElse(new File(pathname)))) {
            workbook.write(fileOut);
            workbook.close();
        } catch (IOException e) {
            throw new WorkbookException("Error saving workbook to file: ".concat(path), e);
        }
    }
}