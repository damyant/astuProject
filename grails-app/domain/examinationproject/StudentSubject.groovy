package examinationproject

import IST.Employee

class StudentSubject {
    /*Mapping among student,subject,programSession*/
    Student student
    Semester semester
    ProgramSession programSession
    SubjectSession subjectSession
    String academicSession
    Date registrationDate
    Date registrationApprovalDate
    Employee approvalBy
    Boolean marksEntered=false
    Status status
    static mapping = {
        marksEntered defaultValue: false
        student column: "student"
        academicSession column: "academicSession"
        registrationDate column: "registrationDate"
        registrationApprovalDate column: "registrationApprovalDate"
        approvalBy column: "approvalBy"
        marksEntered column: "marksEntered"
        programSession column: "programSession"
        semester column: "semester"
        subjectSession column: "subjectSession"
        status column: "status"
    }

    static constraints = {
        student(nullable: false)
        programSession(nullable:false)
        semester(nullable:false)
        subjectSession(nullable:false)
        status(nullable: true)
        approvalBy(nullable: true)
        registrationApprovalDate(nullable: true)
        registrationDate(nullable: true)
        academicSession(nullable: true)
    }
}
