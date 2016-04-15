package postexamination

import grails.transaction.Transactional
import jxl.CellView
import jxl.format.Border
import jxl.format.BorderLineStyle
import jxl.format.UnderlineStyle
import jxl.write.Alignment
import jxl.write.Label
import jxl.write.WritableCell
import jxl.write.WritableCellFormat
import jxl.write.WritableFont
import jxl.write.WritableSheet
import jxl.write.WritableWorkbook
import jxl.write.WriteException
import jxl.write.biff.RowsExceededException

@Transactional
class MarksFoilExcelService {


    private WritableCellFormat timesBoldUnderline;
    private WritableCellFormat times;
    private WritableCellFormat times1;
    private WritableCellFormat times2;
    def serviceMethod() {

    }

    // Start From Here
    Boolean excelReport(params, finalList, course, sheetNo, WritableWorkbook workbook,currentYear,semesterNo){

        WritableSheet sheet= null
        WritableSheet excelSheet=null
        sheet = workbook.createSheet(""+course, sheetNo);
        excelSheet = workbook.getSheet(sheetNo);
        createLabel(excelSheet, course, currentYear,semesterNo);
        createContent(excelSheet, finalList);
        return true

    }

    Boolean excelReport1( studentList,finalList,finalMarksTypeList,subjectCount, course, sheetNo, WritableWorkbook workbook,semesterNo){
        WritableSheet sheet= null
        WritableSheet excelSheet=null
        sheet = workbook.createSheet(""+course, sheetNo);
        excelSheet = workbook.getSheet(sheetNo);
        createLabel1(excelSheet, course,semesterNo);
        createSubLabel(excelSheet,finalMarksTypeList,subjectCount)
        createContent1(excelSheet,studentList, finalList);
//       workbook.write();
//      workbook.close();
        return true

    }

    private void createLabel(WritableSheet sheet, course, currentYear,semesterNo)
            throws WriteException {

        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 12);
        // Define the cell format
        times = new WritableCellFormat(times10pt);
        times1 = new WritableCellFormat(times10pt);
        times2 = new WritableCellFormat(times10pt);
        times.setWrap(true);
        times.setAlignment(Alignment.CENTRE)
        times1.setAlignment(Alignment.LEFT)
        times2.setAlignment(Alignment.LEFT)

        // create create a bold font with unterlines
        WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false,
                UnderlineStyle.NO_UNDERLINE);
        WritableFont times10ptBoldUnderline1 = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false,
                UnderlineStyle.NO_UNDERLINE);
        timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
        times2 = new WritableCellFormat(times10ptBoldUnderline);
        // Lets automatically wrap the cells
        timesBoldUnderline.setWrap(true);
        times2.setWrap(true);
        timesBoldUnderline.setAlignment(Alignment.CENTRE)
        CellView cv = new CellView();
        cv.setFormat(times);

        cv.setFormat(timesBoldUnderline);
        for (int i=0;i< 6;i++){
            int widthInChars = 25;
            sheet.setColumnView(i, widthInChars);
        }

        cv.setAutosize(true);
        int row = 0
        int cols = 1
        WritableCell titleCell = new Label(0, row, "GAUHATI UNIVERSITY ");
        WritableCell titleCell1 = new Label(0, 1, "IDOL (Semester) Examination, "+currentYear);
        WritableCell titleCell2 = new Label(0, 2, "Subject: "+course+"                    Semester: "+semesterNo);
        WritableCell titleCell3 = new Label(0, 3, "Paper: "+course);
        WritableCell titleCell4 = new Label(0, 4, "1st Half/2nd Half                      Total Marks:..........");
        titleCell.setCellFormat(timesBoldUnderline)
        titleCell1.setCellFormat(times1)
        titleCell2.setCellFormat(times2)
        titleCell3.setCellFormat(times2)
        titleCell4.setCellFormat(times1)
        sheet.addCell(titleCell);
        sheet.mergeCells(0, row, cols, row);
        sheet.addCell(titleCell1);
        sheet.mergeCells(0, 1, cols, 1);
        sheet.addCell(titleCell2);
        sheet.mergeCells(0, 2, cols, 2);
        sheet.addCell(titleCell3);
        sheet.mergeCells(0, 3, cols, 3);
        sheet.addCell(titleCell4);
        sheet.mergeCells(0, 4, cols, 4);

        int row1 = 0
        int cols1 = 4
        WritableCell nextTitleCell = new Label(3, row1, "GAUHATI UNIVERSITY ");
        WritableCell nextTitleCell1 = new Label(3, 1, "IDOL (Semester) Examination, "+currentYear);
        WritableCell nextTitleCell2 = new Label(3, 2, "Subject: "+course+"                    Semester: "+semesterNo);
        WritableCell nextTitleCell3 = new Label(3, 3, "Paper: "+course);
        WritableCell nextTitleCell4 = new Label(3, 4, "1st Half/2nd Half                      Total Marks:..........");
        nextTitleCell.setCellFormat(timesBoldUnderline)
        nextTitleCell1.setCellFormat(times1)
        nextTitleCell2.setCellFormat(times2)
        nextTitleCell3.setCellFormat(times2)
        nextTitleCell4.setCellFormat(times1)
        sheet.addCell(nextTitleCell);
        sheet.mergeCells(3, row1, cols1, row1);
        sheet.addCell(nextTitleCell1);
        sheet.mergeCells(3, 1, cols1, 1);
        sheet.addCell(nextTitleCell2);
        sheet.mergeCells(3, 2, cols1, 2);
        sheet.addCell(nextTitleCell3);
        sheet.mergeCells(3, 3, cols1, 3);
        sheet.addCell(nextTitleCell4);
        sheet.mergeCells(3, 4, cols1, 4);

        int fontPointSize = 50;
        int rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);
        fontPointSize = 20;
        rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);

    }

     void createSubLabel(WritableSheet sheet, finalMarksTypeList,subjectCount)
            throws WriteException {
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
        WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false,
                UnderlineStyle.NO_UNDERLINE);
        WritableCellFormat heading1=new WritableCellFormat(times10ptBoldUnderline);
        WritableCellFormat heading2=new WritableCellFormat(times10pt);
        heading1.setWrap(true);
        heading1.setAlignment(Alignment.CENTRE)
        heading1.setBorder(Border.ALL,BorderLineStyle.THIN)
        heading2.setWrap(true);
        heading2.setAlignment(Alignment.CENTRE)
        heading2.setBorder(Border.ALL,BorderLineStyle.THIN)

         WritableCell cell1 = new Label(0, 5, "S.No");
         WritableCell cell2 = new Label(1, 5, "Regn. Number with year");
         WritableCell cell3 = new Label(2, 5, "Roll No.");
         WritableCell cell4 = new Label(3, 5, "Name");
         cell1.setCellFormat(heading1)
         cell2.setCellFormat(heading1)
         cell3.setCellFormat(heading1)
         cell4.setCellFormat(heading1)
         sheet.addCell(cell1);
         sheet.addCell(cell2);
         sheet.addCell(cell3);
         sheet.addCell(cell4);
         def counter=1
         def colIndex=4
         for( def i=0;i<subjectCount;i++){

             WritableCell cell = new Label(colIndex, 5, "Paper"+counter);
             cell.setCellFormat(heading1)

            def mergeCol=0
            for(def j=i;j<=i;j++){
                def subColIndex=colIndex
                mergeCol=finalMarksTypeList[j].size()
                for(def k=0;k<finalMarksTypeList[j].size();k++){
                WritableCell subCell = new Label(subColIndex, 6, finalMarksTypeList[j][k]);
                subCell.setCellFormat(heading2)
                sheet.addCell(subCell);
                    ++subColIndex

                }
            }
             sheet.addCell(cell);
             sheet.mergeCells(colIndex, 5, colIndex+mergeCol-1, 5);
             colIndex+=mergeCol
             ++counter
            if(counter>subjectCount){
             WritableCell cell5 = new Label(colIndex, 5, "Grand Total");
             WritableCell cell6 = new Label(++colIndex, 5, "Result");
             WritableCell cell7 = new Label(++colIndex, 5, "Remarks");
             cell5.setCellFormat(heading1)
             cell6.setCellFormat(heading1)
             cell7.setCellFormat(heading1)
             sheet.addCell(cell5)
             sheet.addCell(cell6)
             sheet.addCell(cell7)
            }




         }




    }


    private void createLabel1(WritableSheet sheet, course,semesterNo)
            throws WriteException {

        // create create a bold font with unterlines
        WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false,
                UnderlineStyle.NO_UNDERLINE);
        WritableFont times10ptBoldUnderline1 = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false,
                UnderlineStyle.NO_UNDERLINE);
        timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
        times2 = new WritableCellFormat(times10ptBoldUnderline);
        // Lets automatically wrap the cells
        timesBoldUnderline.setWrap(true);
        times2.setWrap(true);
        times2.setAlignment(Alignment.CENTRE)
        timesBoldUnderline.setAlignment(Alignment.CENTRE)
        CellView cv = new CellView();
        cv.setFormat(times);

        cv.setFormat(timesBoldUnderline);
        for (int i=0;i< 6;i++){
            int widthInChars = 25;
            sheet.setColumnView(i, widthInChars);
        }

        cv.setAutosize(true);
        int row = 0
        int cols = 1
        WritableCell titleCell = new Label(0, row, "GAUHATI UNIVERSITY ");
//        WritableCell titleCell1 = new Label(0, 1, "IDOL (Semester) Examination, "+currentYear);
        WritableCell titleCell2 = new Label(0, 2, "Institute of Distance and Open Learning, G.U.");
        WritableCell titleCell3 = new Label(0, 3, "MERIT REGISTER");
//        WritableCell titleCell4 = new Label(0, 4, "1st Half/2nd Half                      Total Marks:..........");
        titleCell.setCellFormat(timesBoldUnderline)
//        titleCell1.setCellFormat(times1)
        titleCell2.setCellFormat(times2)
        titleCell3.setCellFormat(times2)
//        titleCell4.setCellFormat(times1)
        sheet.addCell(titleCell);
        sheet.mergeCells(0, row, 7, row);
//        sheet.addCell(titleCell1);
        sheet.mergeCells(0, 1, 7, 1);
        sheet.addCell(titleCell2);
        sheet.mergeCells(0, 2, 7, 2);
        sheet.addCell(titleCell3);
        sheet.mergeCells(0, 3, 7, 3);

        sheet.mergeCells(0, 4, 7, 4);
        int fontPointSize = 50;
        int rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);
        fontPointSize = 20;
        rowHeight = (int) ((1.5d * fontPointSize) * 20);
        sheet.setRowView(row, rowHeight, false);

    }
    void createContent(WritableSheet sheet, List finalList) throws WriteException,
            RowsExceededException {
       // Write a few number
        def counter=0
        for (int i = 0; i < finalList.size(); i++) {
            ++counter;
            addLabel(sheet, 0, i + 5, finalList[i].toString());
            addLabel(sheet, 3, i + 5, finalList[i].toString());
        }
        addCaption(sheet,counter)

    }

    void createContent1(WritableSheet sheet,List studentList, List finalList) throws WriteException,
            RowsExceededException {

        def counter=1
        def rowIndex=7
        for( def a=0;a<studentList.size();a++){
            def index=0

            addLabel(sheet, index, rowIndex, counter.toString());
            addLabel(sheet, ++index, rowIndex, studentList[a].registrationYear.toString());
            addLabel(sheet, ++index, rowIndex, studentList[a].rollNo.toString());
            addLabel(sheet, ++index, rowIndex, studentList[a].firstName.toString());

            for (int j = 0; j < finalList[a].size(); j++) {
               addLabel(sheet, ++index, rowIndex,finalList[a][j].toString());
         }

            def getBackJsonObj1 = grails.converters.JSON.parse(studentList[a].totalMarks)
            def totalMarks= getBackJsonObj1[studentList[a].semester.toString()]
              addLabel(sheet, ++index, rowIndex, totalMarks.toString());
            ++rowIndex
            ++counter
        }

    }


    void addCaption(WritableSheet sheet,counter)
            throws RowsExceededException, WriteException {

        addLabel(sheet, 0, counter + 7, "Examined by:");
        addLabel(sheet, 0, counter + 8, "Scrutinised by:");
        addLabel(sheet, 0, counter + 9, "Head examiner's signature:");

        addLabel(sheet, 3, counter + 7, "Examined by:");
        addLabel(sheet, 3, counter + 8, "Scrutinised by:");
        addLabel(sheet, 3, counter + 9, "Head examiner's signature:");


    }



    void addLabel(WritableSheet sheet, int column, int row, String s)
            throws WriteException, RowsExceededException {
        Label label;
        label = new Label(column, row, s);
        sheet.addCell(label);

    }


    //End Services
}
