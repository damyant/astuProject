package postexamination

class ExamType {
    /*Save details of exam type*/
    String examTypeName
    String requiredTotalMarks
    String marksSecuredSymbol
    String totalMarksSymbol
    String maxInAllStudentsSymbol
    String examTypeTotalSymbol
    static constraints = {
        requiredTotalMarks(nullable: true)
        marksSecuredSymbol(nullable: true)
        totalMarksSymbol(nullable: true)
        maxInAllStudentsSymbol(nullable: true)
        examTypeTotalSymbol(nullable: true)
    }
    static mapping = {
        requiredTotalMarks column: 'requiredTotalMarks'
        examTypeName column: 'ExamType'
        marksSecuredSymbol column: 'marksSecuredSymbol'
        totalMarksSymbol column: 'totalMarksSymbol'
        maxInAllStudentsSymbol column: 'maxInAllStudentsSymbol'
        examTypeTotalSymbol column: 'examTypeTotalSymbol'
    }
}
