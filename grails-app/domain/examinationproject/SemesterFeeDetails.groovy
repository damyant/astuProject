package examinationproject

class SemesterFeeDetails {
    /*saves fee details and maps among student,semester and feeDetails*/
    Student student
    Semester semester
    ProgramDetail programDetail
    String guReceiptNo
    String guReceiptAmount
    Date guReceiptDate
    String academicSession
    Status status
    static mapping = {
        student column: "student"
        semester column: "semester"
        programDetail column: "programDetail"
        guReceiptNo column: "guReceiptNo"
        guReceiptAmount column: "guReceiptAmount"
        guReceiptDate column: 'guReceiptDate'
        academicSession column: "academicSession"
        status column: "status"
    }
    static constraints = {
        student(nullable:false)
        semester(nullable:false)
        programDetail(nullable:false)
        guReceiptNo(nullable:false)
        guReceiptAmount(nullable:false)
        guReceiptDate(nullable:false)
        academicSession(nullable:false)
        status(nullable:false)
    }
}
