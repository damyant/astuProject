package universityproject

import grails.transaction.Transactional
import jxl.CellView
import jxl.format.Colour
import jxl.format.UnderlineStyle
import jxl.write.*
import jxl.write.biff.RowsExceededException

import java.lang.Boolean

@Transactional
class WriteExcelService {
    private WritableCellFormat timesBoldUnderline;
    private WritableCellFormat times;
    private WritableCellFormat times1;

    Boolean excelReport(params,programName,semesterNo, finalList,subjectList, course, sheetNo, WritableWorkbook workbook) {
//       println("creating this sheet "+ finalList)
        WritableSheet sheet = null
        WritableSheet excelSheet = null
        String courseName = "Semester - "+semesterNo
        sheet = workbook.createSheet("" + courseName, sheetNo);
        excelSheet = workbook.getSheet(sheetNo);
        createLabel(excelSheet, params, programName,subjectList);
        createContent(excelSheet, finalList);
        return true
    }

    Boolean excelReportForStudentRegistration(params,finalList, sheetNo, WritableWorkbook workbook){
        WritableSheet sheet = null
        WritableSheet excelSheet = null
        String session = "Academic Session "+params.academisSession1
        sheet = workbook.createSheet("" + session, sheetNo);
        excelSheet = workbook.getSheet(sheetNo);
        createLabelForStudentRegistration(excelSheet, params);
        createContent(excelSheet, finalList);
        return true
    }

    Boolean excelReportForStudentRegistrationPreviousSem(params,finalList, sheetNo, WritableWorkbook workbook){
        WritableSheet sheet = null
        WritableSheet excelSheet = null
        String session = "Program "+params.approvedProgram
        sheet = workbook.createSheet("" + session, sheetNo);
        excelSheet = workbook.getSheet(sheetNo);
        createLabelForStudentRegistrationPreviousSem(excelSheet, params);
        createContent(excelSheet, finalList);
        return true
    }

    Boolean excelReportForGradeDetails(params,finalList, sheetNo, WritableWorkbook workbook){
        WritableSheet sheet = null
        WritableSheet excelSheet = null
        String session = "Program "+params.approvedProgram
        sheet = workbook.createSheet("" + session, sheetNo);
        excelSheet = workbook.getSheet(sheetNo);
        createLabelForGradeDetails(excelSheet, params);
        createContent(excelSheet, finalList);
        return true
    }

    Boolean excelReportForIntake(currentYear,nextYear,finalList,sheetNo, WritableWorkbook workbook) {
//       println("creating this sheet "+ finalList)
        WritableSheet sheet = null
        WritableSheet excelSheet = null
//        String courseName = "Semester - "+semesterNo
        sheet = workbook.createSheet( currentYear+"-"+nextYear, sheetNo);
        excelSheet = workbook.getSheet(sheetNo);
        createLabelForIntake(excelSheet,currentYear,nextYear);
        createContentForIntake(excelSheet, finalList);
        return true
    }

    Boolean excelReportForStudentInfo(progT,progD,finalList,sheetNo, WritableWorkbook workbook) {
//       println("creating this sheet "+ finalList)
        WritableSheet sheet = null
        WritableSheet excelSheet = null
        String courseName = progT+""+progD
        sheet = workbook.createSheet(courseName, sheetNo);
        excelSheet = workbook.getSheet(sheetNo);
        createLabelForStudentInfo(excelSheet,progT,progD);
        createContentForIntake(excelSheet, finalList);
        return true
    }

    Boolean excelReportForEmployeeInfo(finalList,sheetNo, WritableWorkbook workbook) {
//       println("creating this sheet "+ finalList)
        WritableSheet sheet = null
        WritableSheet excelSheet = null
        String courseName = "Employee Info"
        sheet = workbook.createSheet(courseName, sheetNo);
        excelSheet = workbook.getSheet(sheetNo);
        createLabelForEmployeeInfo(excelSheet);
        createContentForIntake(excelSheet, finalList);
        return true
    }

    Boolean excelReportForPassoutStudents(finalList,sheetNo, WritableWorkbook workbook) {
//       println("creating this sheet "+ finalList)
        WritableSheet sheet = null
        WritableSheet excelSheet = null
        String courseName = "PassOut Students"
        sheet = workbook.createSheet(courseName, sheetNo);
        excelSheet = workbook.getSheet(sheetNo);
        createLabelForPassoutStudents(excelSheet);
        createContentForIntake(excelSheet, finalList);
        return true
    }

    Boolean excelReportForStudentGender(progInst,year,gender,finalList,sheetNo, WritableWorkbook workbook) {
//       println("creating this sheet "+ finalList)
        WritableSheet sheet = null
        WritableSheet excelSheet = null
//        String courseName = "Semester - "+semesterNo
        sheet = workbook.createSheet(progInst.courseName+"_"+gender,sheetNo);
        excelSheet = workbook.getSheet(sheetNo);
        createLabelForStudentGender(progInst,year,gender,excelSheet);
        createContentForStudentGender(excelSheet, finalList);
        return true
    }

    Boolean excelReportCategoryStudent(progInst,year,category,finalList,sheetNo, WritableWorkbook workbook) {
//       println("creating this sheet "+ finalList)
        WritableSheet sheet = null
        WritableSheet excelSheet = null
//        String courseName = "Semester - "+semesterNo
        sheet = workbook.createSheet(progInst.courseName,sheetNo);
        excelSheet = workbook.getSheet(sheetNo);
        createLabelCategoryStudent(progInst,year,category,excelSheet);
        createContentForStudentGender(excelSheet, finalList);
        return true
    }

    Boolean excelReportForStudentCategory(cat,year,gender,category,finalList,sheetNo, WritableWorkbook workbook) {
//       println("creating this sheet "+ finalList)
        WritableSheet sheet = null
        WritableSheet excelSheet = null
//        String courseName = "Semester - "+semesterNo
        sheet = workbook.createSheet(cat,sheetNo);
        excelSheet = workbook.getSheet(sheetNo);
        createLabelForStudentCategory(year,gender,category,excelSheet);
        createContentForStudentCategory(excelSheet, finalList);
        return true
    }

    Boolean excelReportMinorityStudent(finalList,sheetNo, WritableWorkbook workbook) {
//       println("creating this sheet "+ finalList)
        WritableSheet sheet = null
        WritableSheet excelSheet = null
//        String courseName = "Semester - "+semesterNo
        def min="Minority Student"
        sheet = workbook.createSheet(min,sheetNo);
        excelSheet = workbook.getSheet(sheetNo);
        createLabelForMinorityStudent(excelSheet);
        createContentForStudentCategory(excelSheet, finalList);
        return true
    }

    private void createLabel(WritableSheet sheet, params, programName,subjectList)
            throws WriteException {

        if (params.studentCategory == 'null') {
            params.studentCategory = null
        }
        // Lets create a times font
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 12);
        // Define the cell format
        times = new WritableCellFormat(times10pt);
        times1 = new WritableCellFormat(times10pt);
        // Lets automatically wrap the cells
        times.setWrap(true);
        times.setAlignment(Alignment.CENTRE)
        times1.setAlignment(Alignment.LEFT)
        // create create a bold font with unterlines
        WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false,
                UnderlineStyle.NO_UNDERLINE);
        timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
        // Lets automatically wrap the cells
        timesBoldUnderline.setWrap(true);
        timesBoldUnderline.setAlignment(Alignment.CENTRE)
        CellView cv = new CellView();
        cv.setFormat(times);
        times10ptBoldUnderline.setColour(Colour.BLUE);
        cv.setFormat(timesBoldUnderline);
        for (int i = 0; i < 4; i++) {
            int widthInChars = 25;
            sheet.setColumnView(i, widthInChars);
        }
        cv.setAutosize(true);
        int row = 0
        int cols = 3+subjectList.size()
        WritableCell titleCell = new Label(0, row, "Students In "+programName);
        titleCell.setCellFormat(times)
        sheet.addCell(titleCell);
        sheet.mergeCells(0, row, cols, row);
        int fontPointSize = 50;
        int rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);
        fontPointSize = 20;
        rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);
        addCaption(sheet, 0, 1, "Sr. No.");
        addCaption(sheet, 1, 1, "Name ");
        addCaption(sheet, 2, 1, "Roll No ");
        addCaption(sheet, 3, 1, "Semester");
        def i= 0
        subjectList.each{

            addCaption(sheet, 4+i,1, it.subjectCode)
            i++
        }
    }

    private void createLabelForStudentRegistration(WritableSheet sheet, params)
            throws WriteException {

        if (params.studentCategory == 'null') {
            params.studentCategory = null
        }
        // Lets create a times font
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 12);
        // Define the cell format
        times = new WritableCellFormat(times10pt);
        times1 = new WritableCellFormat(times10pt);
        // Lets automatically wrap the cells
        times.setWrap(true);
        times.setAlignment(Alignment.CENTRE)
        times1.setAlignment(Alignment.LEFT)
        // create create a bold font with unterlines
        WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false,
                UnderlineStyle.NO_UNDERLINE);
        timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
        // Lets automatically wrap the cells
        timesBoldUnderline.setWrap(true);
        timesBoldUnderline.setAlignment(Alignment.CENTRE)
        CellView cv = new CellView();
        cv.setFormat(times);
        times10ptBoldUnderline.setColour(Colour.BLUE);
        cv.setFormat(timesBoldUnderline);
        for (int i = 0; i < 7; i++) {
            if(i==6){
                int widthInChars = 55;
                sheet.setColumnView(i, widthInChars);
            }
            else{
                int widthInChars = 25;
                sheet.setColumnView(i, widthInChars);
            }

        }
        cv.setAutosize(true);
        int row = 0
        int cols = 7
        WritableCell titleCell = new Label(0, row, "Academic Session"+params.academicSession1);
        titleCell.setCellFormat(times)
        sheet.addCell(titleCell);
        sheet.mergeCells(0, row, cols, row);
        int fontPointSize = 50;
        int rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);
        fontPointSize = 20;
        rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);
        addCaption(sheet, 0, 1, "Sr. No.");
        addCaption(sheet, 1, 1, "Roll No ");
        addCaption(sheet, 2, 1, "Name ");
        addCaption(sheet, 3, 1, "Admission Date");
        addCaption(sheet, 4, 1, "Program");
        addCaption(sheet, 5, 1, "Semester");
        addCaption(sheet, 6, 1, "Subjects");
        addCaption(sheet, 7, 1, "Receipt No");
        addCaption(sheet, 8, 1, "Receipt Amount");
        addCaption(sheet, 9, 1, "Receipt Date");

    }

    private void createLabelForStudentRegistrationPreviousSem(WritableSheet sheet, params)
            throws WriteException {

        if (params.studentCategory == 'null') {
            params.studentCategory = null
        }
        // Lets create a times font
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 12);
        // Define the cell format
        times = new WritableCellFormat(times10pt);
        times1 = new WritableCellFormat(times10pt);
        // Lets automatically wrap the cells
        times.setWrap(true);
        times.setAlignment(Alignment.CENTRE)
        times1.setAlignment(Alignment.LEFT)
        // create create a bold font with unterlines
        WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false,
                UnderlineStyle.NO_UNDERLINE);
        timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
        // Lets automatically wrap the cells
        timesBoldUnderline.setWrap(true);
        timesBoldUnderline.setAlignment(Alignment.CENTRE)
        CellView cv = new CellView();
        cv.setFormat(times);
        times10ptBoldUnderline.setColour(Colour.BLUE);
        cv.setFormat(timesBoldUnderline);
        for (int i = 0; i < 7; i++) {
            if(i==6){
                int widthInChars = 55;
                sheet.setColumnView(i, widthInChars);
            }
            else{
                int widthInChars = 25;
                sheet.setColumnView(i, widthInChars);
            }

        }
        cv.setAutosize(true);
        int row = 0
        int cols = 7
        WritableCell titleCell = new Label(0, row, "Program"+params.approvedProgram);
        titleCell.setCellFormat(times)
        sheet.addCell(titleCell);
        sheet.mergeCells(0, row, cols, row);
        int fontPointSize = 50;
        int rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);
        fontPointSize = 20;
        rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);
        addCaption(sheet, 0, 1, "Sr. No.");
        addCaption(sheet, 1, 1, "Roll No ");
        addCaption(sheet, 2, 1, "Name ");
        addCaption(sheet, 3, 1, "Program");
        addCaption(sheet, 4, 1, "Semester");
        addCaption(sheet, 5, 1, "Subjects");
        addCaption(sheet, 6, 1, "Receipt No");
        addCaption(sheet, 7, 1, "Receipt Amount");
        addCaption(sheet, 8, 1, "Receipt Date");
        addCaption(sheet, 9, 1, "Back papers");

    }

    private void createLabelForGradeDetails(WritableSheet sheet, params)
            throws WriteException {

        if (params.studentCategory == 'null') {
            params.studentCategory = null
        }
        // Lets create a times font
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 12);
        // Define the cell format
        times = new WritableCellFormat(times10pt);
        times1 = new WritableCellFormat(times10pt);
        // Lets automatically wrap the cells
        times.setWrap(true);
        times.setAlignment(Alignment.CENTRE)
        times1.setAlignment(Alignment.LEFT)
        // create create a bold font with unterlines
        WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false,
                UnderlineStyle.NO_UNDERLINE);
        timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
        // Lets automatically wrap the cells
        timesBoldUnderline.setWrap(true);
        timesBoldUnderline.setAlignment(Alignment.CENTRE)
        CellView cv = new CellView();
        cv.setFormat(times);
        times10ptBoldUnderline.setColour(Colour.BLUE);
        cv.setFormat(timesBoldUnderline);
        for (int i = 0; i < 7; i++) {
            if(i==6){
                int widthInChars = 55;
                sheet.setColumnView(i, widthInChars);
            }
            else{
                int widthInChars = 25;
                sheet.setColumnView(i, widthInChars);
            }

        }
        cv.setAutosize(true);
        int row = 0
        int cols = 7
        WritableCell titleCell = new Label(0, row, "Program"+params.approvedProgram);
        titleCell.setCellFormat(times)
        sheet.addCell(titleCell);
        sheet.mergeCells(0, row, cols, row);
        int fontPointSize = 50;
        int rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);
        fontPointSize = 20;
        rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);
        addCaption(sheet, 0, 1, "Sr. No.");
        addCaption(sheet, 1, 1, "Roll No ");
        addCaption(sheet, 2, 1, "Name ");
        addCaption(sheet, 3, 1, "Program");

        addCaption(sheet, 4, 1, "Subject");
        addCaption(sheet, 5, 1, "Marks");
        addCaption(sheet, 6, 1, "Grades");


    }


    private void createLabelForIntake(WritableSheet sheet,currentYear,nextYear)
            throws WriteException {


        // Lets create a times font
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 12);
        // Define the cell format
        times = new WritableCellFormat(times10pt);
        times1 = new WritableCellFormat(times10pt);
        // Lets automatically wrap the cells
        times.setWrap(true);
        times.setAlignment(Alignment.CENTRE)
        times1.setAlignment(Alignment.LEFT)
        // create create a bold font with unterlines
        WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false,
                UnderlineStyle.NO_UNDERLINE);
        timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
        // Lets automatically wrap the cells
        timesBoldUnderline.setWrap(true);
        timesBoldUnderline.setAlignment(Alignment.CENTRE)
        CellView cv = new CellView();
        cv.setFormat(times);
        times10ptBoldUnderline.setColour(Colour.BLUE);
        cv.setFormat(timesBoldUnderline);
        for (int i = 0; i < 4; i++) {
            int widthInChars = 25;
            sheet.setColumnView(i, widthInChars);
        }
        cv.setAutosize(true);
        int row = 0
        int cols = 19
        WritableCell titleCell = new Label(0, row, "Students For Intake "+ currentYear+'-'+nextYear);
        titleCell.setCellFormat(times)
        sheet.addCell(titleCell);
        sheet.mergeCells(0, row, cols, row);
        int fontPointSize = 50;
        int rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);
        fontPointSize = 20;
        rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);
        addCaption(sheet, 0, 1, "Sr. No.");
        addCaption(sheet, 1, 1, "Program ");
        addCaption(sheet, 2, 1, "Course");
        addCaption(sheet, 3, 1, "Approved Intake");
        addCaption(sheet, 4, 1, "General Male");
        addCaption(sheet, 5, 1, "General Female");
        addCaption(sheet, 6, 1, "OBC Male");
        addCaption(sheet, 7, 1, "OBC Female");
        addCaption(sheet, 8, 1, "SC Male");
        addCaption(sheet, 9, 1, "SC Female");
        addCaption(sheet, 10, 1, "ST Male");
        addCaption(sheet, 11, 1, "ST Female");
        addCaption(sheet, 12, 1, "PH Male");
        addCaption(sheet, 13, 1, "PH Female");
        addCaption(sheet, 14, 1, "Minority Male");
        addCaption(sheet, 15, 1, "Minority Female");
        addCaption(sheet, 16, 1, "TFW Male");
        addCaption(sheet, 17, 1, "TFW Female");
        addCaption(sheet, 18, 1, "Total students");

    }

    private void createLabelForStudentInfo(WritableSheet sheet,progT,progD)
            throws WriteException {


        // Lets create a times font
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 12);
        // Define the cell format
        times = new WritableCellFormat(times10pt);
        times1 = new WritableCellFormat(times10pt);
        // Lets automatically wrap the cells
        times.setWrap(true);
        times.setAlignment(Alignment.CENTRE)
        times1.setAlignment(Alignment.LEFT)
        // create create a bold font with unterlines
        WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false,
                UnderlineStyle.NO_UNDERLINE);
        timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
        // Lets automatically wrap the cells
        timesBoldUnderline.setWrap(true);
        timesBoldUnderline.setAlignment(Alignment.CENTRE)
        CellView cv = new CellView();
        cv.setFormat(times);
        times10ptBoldUnderline.setColour(Colour.BLUE);
        cv.setFormat(timesBoldUnderline);
        for (int i = 0; i < 7; i++) {
            int widthInChars = 25;
            sheet.setColumnView(i, widthInChars);
        }
        cv.setAutosize(true);
        int row = 0
        int cols = 5
        WritableCell titleCell = new Label(0, row, "Student's Information for PROGRAM:-"+progT+" DEPARTMENT:-"+progD);
        titleCell.setCellFormat(times)
        sheet.addCell(titleCell);
        sheet.mergeCells(0, row, cols, row);
        int fontPointSize = 50;
        int rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);
        fontPointSize = 20;
        rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);
        addCaption(sheet, 0, 1, "Sr.No.");
        addCaption(sheet, 1, 1, "Roll No");
        addCaption(sheet, 2, 1, "Name");
        addCaption(sheet, 3, 1, "Program");
        addCaption(sheet, 4, 1, "Branch");
        addCaption(sheet, 5, 1, "Semester");
        addCaption(sheet, 6, 1, "Gender");



    }

    private void createLabelForEmployeeInfo(WritableSheet sheet)
            throws WriteException {


        // Lets create a times font
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 12);
        // Define the cell format
        times = new WritableCellFormat(times10pt);
        times1 = new WritableCellFormat(times10pt);
        // Lets automatically wrap the cells
        times.setWrap(true);
        times.setAlignment(Alignment.CENTRE)
        times1.setAlignment(Alignment.LEFT)
        // create create a bold font with unterlines
        WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false,
                UnderlineStyle.NO_UNDERLINE);
        timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
        // Lets automatically wrap the cells
        timesBoldUnderline.setWrap(true);
        timesBoldUnderline.setAlignment(Alignment.CENTRE)
        CellView cv = new CellView();
        cv.setFormat(times);
        times10ptBoldUnderline.setColour(Colour.BLUE);
        cv.setFormat(timesBoldUnderline);
        for (int i = 0; i < 10; i++) {
            int widthInChars = 25;
            sheet.setColumnView(i, widthInChars);
        }
        cv.setAutosize(true);
        int row = 0
        int cols = 9
        WritableCell titleCell = new Label(0, row, "Employee's Information");
        titleCell.setCellFormat(times)
        sheet.addCell(titleCell);
        sheet.mergeCells(0, row, cols, row);
        int fontPointSize = 50;
        int rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);
        fontPointSize = 20;
        rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);
        addCaption(sheet, 0, 1, "Sr.No.");
        addCaption(sheet, 1, 1, "Employee Code");
        addCaption(sheet, 2, 1, "Name");
        addCaption(sheet, 3, 1, "Gender");
        addCaption(sheet, 4, 1, "Father's Name");
        addCaption(sheet, 5, 1, "Mother's Name");
        addCaption(sheet, 6, 1, "Email");
        addCaption(sheet, 7, 1, "Mobile No");
        addCaption(sheet, 8, 1, "Department");
        addCaption(sheet, 9, 1, "Address");



    }

    private void createLabelForPassoutStudents(WritableSheet sheet)
            throws WriteException {


        // Lets create a times font
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 12);
        // Define the cell format
        times = new WritableCellFormat(times10pt);
        times1 = new WritableCellFormat(times10pt);
        // Lets automatically wrap the cells
        times.setWrap(true);
        times.setAlignment(Alignment.CENTRE)
        times1.setAlignment(Alignment.LEFT)
        // create create a bold font with unterlines
        WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false,
                UnderlineStyle.NO_UNDERLINE);
        timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
        // Lets automatically wrap the cells
        timesBoldUnderline.setWrap(true);
        timesBoldUnderline.setAlignment(Alignment.CENTRE)
        CellView cv = new CellView();
        cv.setFormat(times);
        times10ptBoldUnderline.setColour(Colour.BLUE);
        cv.setFormat(timesBoldUnderline);
        for (int i = 0; i < 4; i++) {
            int widthInChars = 25;
            sheet.setColumnView(i, widthInChars);
        }
        cv.setAutosize(true);
        int row = 0
        int cols = 9
        WritableCell titleCell = new Label(0, row, "PassOut Students");
        titleCell.setCellFormat(times)
        sheet.addCell(titleCell);
        sheet.mergeCells(0, row, cols, row);
        int fontPointSize = 50;
        int rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);
        fontPointSize = 20;
        rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);
        addCaption(sheet, 0, 1, "Sr.No.");
        addCaption(sheet, 1, 1, "Roll No");
        addCaption(sheet, 2, 1, "Name");
        addCaption(sheet, 3, 1, "Program");




    }

    private void createLabelForMinorityStudent(WritableSheet sheet)
            throws WriteException {


        // Lets create a times font
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 12);
        // Define the cell format
        times = new WritableCellFormat(times10pt);
        times1 = new WritableCellFormat(times10pt);
        // Lets automatically wrap the cells
        times.setWrap(true);
        times.setAlignment(Alignment.CENTRE)
        times1.setAlignment(Alignment.LEFT)
        // create create a bold font with unterlines
        WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false,
                UnderlineStyle.NO_UNDERLINE);
        timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
        // Lets automatically wrap the cells
        timesBoldUnderline.setWrap(true);
        timesBoldUnderline.setAlignment(Alignment.CENTRE)
        CellView cv = new CellView();
        cv.setFormat(times);
        times10ptBoldUnderline.setColour(Colour.BLUE);
        cv.setFormat(timesBoldUnderline);
        for (int i = 0; i < 7; i++) {
            int widthInChars = 25;
            sheet.setColumnView(i, widthInChars);
        }
        cv.setAutosize(true);
        int row = 0
        int cols = 6
        WritableCell titleCell = new Label(0, row, "Minority Student ");
        titleCell.setCellFormat(times)
        sheet.addCell(titleCell);
        sheet.mergeCells(0, row, cols, row);
        int fontPointSize = 50;
        int rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);
        fontPointSize = 20;
        rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);
        addCaption(sheet, 0, 1, "Sr.No.");
        addCaption(sheet, 1, 1, "Roll No");
        addCaption(sheet, 2, 1, "Name");
        addCaption(sheet, 3, 1, "Program");
        addCaption(sheet, 4, 1, "Minority");
        addCaption(sheet, 5, 1, "Semester");
        addCaption(sheet, 6, 1, "Gender");



    }

    private void createLabelForStudentCategory(year,gender,category,WritableSheet sheet)
            throws WriteException {


        // Lets create a times font
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 12);
        // Define the cell format
        times = new WritableCellFormat(times10pt);
        times1 = new WritableCellFormat(times10pt);
        // Lets automatically wrap the cells
        times.setWrap(true);
        times.setAlignment(Alignment.CENTRE)
        times1.setAlignment(Alignment.LEFT)
        // create create a bold font with unterlines
        WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false,
                UnderlineStyle.NO_UNDERLINE);
        timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
        // Lets automatically wrap the cells
        timesBoldUnderline.setWrap(true);
        timesBoldUnderline.setAlignment(Alignment.CENTRE)
        CellView cv = new CellView();
        cv.setFormat(times);
        times10ptBoldUnderline.setColour(Colour.BLUE);
        cv.setFormat(timesBoldUnderline);
        for (int i = 0; i < 6; i++) {
            int widthInChars = 25;
            sheet.setColumnView(i, widthInChars);
        }
        cv.setAutosize(true);
        int row = 0
        int cols = 5
        WritableCell titleCell = new Label(0, row, "Student's Category "+category+" "+gender);
        titleCell.setCellFormat(times)
        sheet.addCell(titleCell);
        sheet.mergeCells(0, row, cols, row);
        int fontPointSize = 50;
        int rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);
        fontPointSize = 20;
        rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);
        addCaption(sheet, 0, 1, "Sr. No.");
        addCaption(sheet, 1, 1, "Name ");
        addCaption(sheet, 2, 1, "Roll No");
        addCaption(sheet, 3, 1, "Semester");
        addCaption(sheet, 4, 1, "Father Name");
        addCaption(sheet, 5, 1, "Mother Name");


    }
    private void createLabelCategoryStudent(progInst,year,category,WritableSheet sheet)
            throws WriteException {


        // Lets create a times font
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 12);
        // Define the cell format
        times = new WritableCellFormat(times10pt);
        times1 = new WritableCellFormat(times10pt);
        // Lets automatically wrap the cells
        times.setWrap(true);
        times.setAlignment(Alignment.CENTRE)
        times1.setAlignment(Alignment.LEFT)
        // create create a bold font with unterlines
        WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false,
                UnderlineStyle.NO_UNDERLINE);
        timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
        // Lets automatically wrap the cells
        timesBoldUnderline.setWrap(true);
        timesBoldUnderline.setAlignment(Alignment.CENTRE)
        CellView cv = new CellView();
        cv.setFormat(times);
        times10ptBoldUnderline.setColour(Colour.BLUE);
        cv.setFormat(timesBoldUnderline);
        for (int i = 0; i < 7; i++) {
            int widthInChars = 25;
            sheet.setColumnView(i, widthInChars);
        }
        cv.setAutosize(true);
        int row = 0
        int cols =6
        WritableCell titleCell = new Label(0, row, "List of "+category+" category students in "+progInst.courseName+"_"+year);
        titleCell.setCellFormat(times)
        sheet.addCell(titleCell);
        sheet.mergeCells(0, row, cols, row);
        int fontPointSize = 50;
        int rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);
        fontPointSize = 20;
        rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);
        addCaption(sheet, 0, 1, "Sr. No.");
        addCaption(sheet, 1, 1, "Name ");
        addCaption(sheet, 2, 1, "Roll No");
        addCaption(sheet, 3, 1, "Category");
        addCaption(sheet, 4, 1, "Semester");
        addCaption(sheet, 5, 1, "Father Name");
        addCaption(sheet, 6, 1, "Mother Name");


    }

    private void createLabelForStudentGender(progInst,year,gender,WritableSheet sheet)
            throws WriteException {


        // Lets create a times font
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 12);
        // Define the cell format
        times = new WritableCellFormat(times10pt);
        times1 = new WritableCellFormat(times10pt);
        // Lets automatically wrap the cells
        times.setWrap(true);
        times.setAlignment(Alignment.CENTRE)
        times1.setAlignment(Alignment.LEFT)
        // create create a bold font with unterlines
        WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false,
                UnderlineStyle.NO_UNDERLINE);
        timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
        // Lets automatically wrap the cells
        timesBoldUnderline.setWrap(true);
        timesBoldUnderline.setAlignment(Alignment.CENTRE)
        CellView cv = new CellView();
        cv.setFormat(times);
        times10ptBoldUnderline.setColour(Colour.BLUE);
        cv.setFormat(timesBoldUnderline);
        for (int i = 0; i < 7; i++) {
            int widthInChars = 25;
            sheet.setColumnView(i, widthInChars);
        }
        cv.setAutosize(true);
        int row = 0
        int cols = 6
        WritableCell titleCell = new Label(0, row,"All "+gender+" students in "+progInst.courseName+"_"+year);
        titleCell.setCellFormat(times)
        sheet.addCell(titleCell);
        sheet.mergeCells(0, row, cols, row);
        int fontPointSize = 50;
        int rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);
        fontPointSize = 20;
        rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);
        addCaption(sheet, 0, 1, "Sr. No.");
        addCaption(sheet, 1, 1, "Name ");
        addCaption(sheet, 2, 1, "Roll No");
        addCaption(sheet, 3, 1, "Semester");
        addCaption(sheet, 4, 1, "Gender");
        addCaption(sheet, 5, 1, "Father Name");
        addCaption(sheet, 6, 1, "Mother Name");


    }
    void createContentForIntake(WritableSheet sheet, List finalList) throws WriteException,
            RowsExceededException {
//        println("now creating contents")
        // Write a few number
        for (int i = 0; i < finalList.size(); i++) {
            int j = 0
            addLabel(sheet, j, i + 2, (i + 1).toString());
            for(; j< finalList[i].size();j++)
            {

                addLabel(sheet, j + 1, i + 2, finalList[i][j].toString());
            }

//            addLabel(sheet, j + 1, i + 2, finalList[i].firstName + ' ' + (finalList[i].middleName ? finalList[i].middleName : '') + ' ' + finalList[i].lastName);
//            addLabel(sheet, j + 2, i + 2, (finalList[i].rollNo));
//            addLabel(sheet, j + 2, i + 2, (finalList[i].semesterNo));
//            addLabel(sheet, j + 3, i + 2, finalList[i].studyCentre[0].name);
        }

    }


    void createContentForStudentCategory(WritableSheet sheet, List finalList) throws WriteException,
            RowsExceededException {
//        println("now creating contents")
        // Write a few number
        for (int i = 0; i < finalList.size(); i++) {
            int j = 0
            addLabel(sheet, j, i + 2, (i + 1).toString());
            for(; j< finalList[i].size();j++)
            {

                addLabel(sheet, j + 1, i + 2, finalList[i][j].toString());
            }

//            addLabel(sheet, j + 1, i + 2, finalList[i].firstName + ' ' + (finalList[i].middleName ? finalList[i].middleName : '') + ' ' + finalList[i].lastName);
//            addLabel(sheet, j + 2, i + 2, (finalList[i].rollNo));
//            addLabel(sheet, j + 2, i + 2, (finalList[i].semesterNo));
//            addLabel(sheet, j + 3, i + 2, finalList[i].studyCentre[0].name);
        }

    }

    void createContentForStudentGender(WritableSheet sheet, List finalList) throws WriteException,
            RowsExceededException {
//        println("now creating contents")
        // Write a few number
        for (int i = 0; i < finalList.size(); i++) {
            int j = 0
            addLabel(sheet, j, i + 2, (i + 1).toString());
            for(; j< finalList[i].size();j++)
            {

                addLabel(sheet, j + 1, i + 2, finalList[i][j].toString());
            }

//            addLabel(sheet, j + 1, i + 2, finalList[i].firstName + ' ' + (finalList[i].middleName ? finalList[i].middleName : '') + ' ' + finalList[i].lastName);
//            addLabel(sheet, j + 2, i + 2, (finalList[i].rollNo));
//            addLabel(sheet, j + 2, i + 2, (finalList[i].semesterNo));
//            addLabel(sheet, j + 3, i + 2, finalList[i].studyCentre[0].name);
        }

    }
    void addCaption(WritableSheet sheet, int column, int row, String s)
        throws RowsExceededException, WriteException {
        Label label;
        label = new Label(column, row, s, timesBoldUnderline);
        sheet.addCell(label);
    }


    void createContent(WritableSheet sheet, List finalList) throws WriteException,
            RowsExceededException {
//        println("now creating contents")
        // Write a few number
        for (int i = 0; i < finalList.size(); i++) {
            int j = 0
            addLabel(sheet, j, i + 2, (i + 1).toString());
            for(; j< finalList[i].size();j++)
            {

            addLabel(sheet, j + 1, i + 2, finalList[i][j].toString());
            }

//            addLabel(sheet, j + 1, i + 2, finalList[i].firstName + ' ' + (finalList[i].middleName ? finalList[i].middleName : '') + ' ' + finalList[i].lastName);
//            addLabel(sheet, j + 2, i + 2, (finalList[i].rollNo));
//            addLabel(sheet, j + 2, i + 2, (finalList[i].semesterNo));
//            addLabel(sheet, j + 3, i + 2, finalList[i].studyCentre[0].name);
        }

    }


    void addNumber(WritableSheet sheet, int column, int row, Integer integer) throws WriteException, RowsExceededException {
        Number number;
        number = new Number(column, row, integer, times);
        sheet.addCell(number);
    }

    void addLabel(WritableSheet sheet, int column, int row, String s)
            throws WriteException, RowsExceededException {
        Label label;
        label = new Label(column, row, s, times);
        sheet.addCell(label);
    }

}
