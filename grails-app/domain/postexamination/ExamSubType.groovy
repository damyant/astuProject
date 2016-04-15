package postexamination

class ExamSubType {
    /*Domain for saving details of exam subType & mapping with exam type*/
    String examTypeId
    String examSubTypeName
    String marksSecuredSymbol
    String totalMarksSymbol
    String maxInAllStudentsSymbol
    String examSubTypeTotalSymbol

    static constraints = {
        marksSecuredSymbol(nullable: true)
        totalMarksSymbol(nullable: true)
        maxInAllStudentsSymbol(nullable: true)
        examSubTypeTotalSymbol(nullable: true)
    }
    static mapping = {
        examTypeId column: 'examTypeId'
        examSubTypeName column: 'examSubTypeName'
        marksSecuredSymbol column: 'marksSecuredSymbol'
        totalMarksSymbol column: 'totalMarksSymbol'
        maxInAllStudentsSymbol column: 'maxInAllStudentsSymbol'
        examSubTypeTotalSymbol column: 'examSubTypeTotalSymbol'
    }
}
